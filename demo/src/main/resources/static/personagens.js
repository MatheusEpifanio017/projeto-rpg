
const createButton = document.getElementById("create-button");
const cancelButton = document.getElementById("cancel-button");
const characterForm = document.getElementById("character-form");
const characterFormSection = document.getElementById("character-form-section");
const characterList = document.getElementById("character-list");
const raceSelect = document.getElementById("character-race");
const classSelect = document.getElementById("character-class");
const characterNameInput = document.getElementById("character-name");
const characterBackstoryInput = document.getElementById("character-backstory");
const saveButton = document.getElementById("save-button");
const detailsModal = document.getElementById("character-details-modal");

const SUPABASE_URL = "https://leummipycreyhzvmoope.supabase.co";
const SUPABASE_KEY = "sb_publishable_P5hdzuDrEfBiMQYjiCC0Pg_zhJvgH1B";

const NOMES_ATRIBUTOS = {
  forca: "Força",
  destreza: "Destreza",
  constituicao: "Constituição",
  inteligencia: "Inteligência",
  sabedoria: "Sabedoria",
  carisma: "Carisma"
};

let supabaseClient = null;
let jogadorId = null;
let personagens = [];
let racasCatalogo = [];
let classesCatalogo = [];
let catalogosCarregados = false;
let salvandoPersonagem = false;

function elemento(id) {
  return document.getElementById(id);
}

function mostrarTexto(id, valor, alternativa = "Não informado") {
  const campo = elemento(id);

  if (!campo) {
    return;
  }

  campo.textContent =
    valor !== null && valor !== undefined && valor !== ""
      ? valor
      : alternativa;
}

function criarItens(containerId, itens, formatarNome) {
  const container = elemento(containerId);

  if (!container) {
    return;
  }

  container.replaceChildren();

  Object.entries(itens).forEach(([chave, valor]) => {
    const item = document.createElement("div");
    item.className = "attribute-item";

    const nome = document.createElement("span");
    nome.textContent = formatarNome(chave);

    const numero = document.createElement("strong");
    numero.textContent = valor;

    item.append(nome, numero);
    container.appendChild(item);
  });
}

function mostrarAtributos(containerId, atributos) {
  if (!atributos) {
    return;
  }

  const valores = {};

  Object.keys(NOMES_ATRIBUTOS).forEach((chave) => {
    valores[chave] = atributos[chave] ?? "—";
  });

  criarItens(
    containerId,
    valores,
    (chave) => NOMES_ATRIBUTOS[chave] || chave
  );
}

function mostrarLista(containerId, itens, mensagemVazia) {
  const lista = elemento(containerId);

  if (!lista) {
    return;
  }

  lista.replaceChildren();

  if (!Array.isArray(itens) || itens.length === 0) {
    const li = document.createElement("li");
    li.textContent = mensagemVazia;
    lista.appendChild(li);
    return;
  }

  itens.forEach((texto) => {
    const li = document.createElement("li");
    li.textContent = texto;
    lista.appendChild(li);
  });
}

function obterBonus(raca) {
  return raca?.bonusAtributos || {};
}

function obterPassiva(raca) {
  return raca?.passiva || {};
}

function energiaDaClasse(nome) {
  if (nome === "Feiticeiro") {
    return 6;
  }

  if (nome === "Clérigo") {
    return 4;
  }

  return 3;
}

function calcularModificador(valor) {
  return Math.floor((valor - 10) / 2);
}

function calcularAtributosFinais(classe, raca) {
  const base = classe?.atributosBase || {};
  const bonus = obterBonus(raca);
  const finais = {};

  Object.keys(NOMES_ATRIBUTOS).forEach((chave) => {
    finais[chave] =
      Number(base[chave] ?? 10) + Number(bonus[chave] ?? 0);
  });

  return finais;
}

function atualizarDetalhesRaca() {
  const raca = racasCatalogo.find(
    (item) => item.nome === raceSelect.value
  );

  const painel = elemento("race-details");

  if (!raca || !painel) {
    if (painel) {
      painel.hidden = true;
    }

    atualizarPrevia();
    return;
  }

  const passiva = obterPassiva(raca);
  painel.hidden = false;

  mostrarTexto("race-title", raca.nome);
  mostrarTexto(
    "race-description",
    raca.descricao,
    "Características da raça escolhida."
  );
  mostrarTexto("race-passive-name", passiva.nome, "Passiva racial");
  mostrarTexto(
    "race-passive-description",
    passiva.descricao,
    "Nenhuma descrição cadastrada."
  );
  mostrarTexto(
    "race-passive-condition",
    passiva.condicao ??
      passiva.condicaoAtivacao ??
      passiva.gatilho,
    "Consulte a descrição da passiva para saber quando ela se aplica."
  );

  mostrarAtributos("race-bonuses", obterBonus(raca));
  atualizarPrevia();
}

function atualizarDetalhesClasse() {
  const classe = classesCatalogo.find(
    (item) => item.nome === classSelect.value
  );

  const painel = elemento("class-details");

  if (!classe || !painel) {
    if (painel) {
      painel.hidden = true;
    }

    atualizarPrevia();
    return;
  }

  painel.hidden = false;

  mostrarTexto("class-title", classe.nome);
  mostrarTexto(
    "class-description",
    classe.descricao,
    "Descrição da classe não cadastrada."
  );
  mostrarTexto("class-hp", classe.pontosVidaBase);
  mostrarTexto("class-energy", energiaDaClasse(classe.nome));

  mostrarAtributos("class-attributes", classe.atributosBase);
  mostrarLista(
    "class-abilities",
    classe.habilidadesIniciais,
    "Nenhuma habilidade inicial cadastrada."
  );

  atualizarPrevia();
}

function atualizarPrevia() {
  const painel = elemento("character-preview");
  const nome = characterNameInput.value.trim();

  const raca = racasCatalogo.find(
    (item) => item.nome === raceSelect.value
  );

  const classe = classesCatalogo.find(
    (item) => item.nome === classSelect.value
  );

  if (!painel) {
    return;
  }

  if (!nome || !raca || !classe) {
    painel.hidden = true;
    return;
  }

  const atributosFinais = calcularAtributosFinais(classe, raca);
  const vida = Number(classe.pontosVidaBase ?? 0);
  const defesa = 10 + calcularModificador(atributosFinais.destreza);
  const energia = energiaDaClasse(classe.nome);
  const passiva = obterPassiva(raca);

  painel.hidden = false;

  mostrarTexto("preview-name", nome);
  mostrarTexto(
    "preview-identity",
    `${raca.nome} • ${classe.nome} • Nível 1`
  );
  mostrarTexto("preview-hp", `${vida} / ${vida} PV`);
  mostrarTexto("preview-defense", defesa);
  mostrarTexto("preview-energy", `${energia} / ${energia}`);

  mostrarAtributos("preview-attributes", atributosFinais);

  mostrarTexto(
    "preview-passive",
    `${passiva.nome || "Passiva racial"}: ${
      passiva.descricao || "Consulte os detalhes da raça."
    }`
  );

  mostrarLista(
    "preview-abilities",
    classe.habilidadesIniciais,
    "Nenhuma habilidade inicial cadastrada."
  );
}

function normalizarPersonagem(item) {
  return {
    id: item.id,
    name: item.nome,
    race: item.raca,
    characterClass: item.classe,
    level: item.nivel ?? 1,
    backstory: item.historia ?? item.backstory ?? "",
    forca: item.forca,
    destreza: item.destreza,
    constituicao: item.constituicao,
    inteligencia: item.inteligencia,
    sabedoria: item.sabedoria,
    carisma: item.carisma,
    vidaMaxima: item.vidaMaxima ?? item.vida_maxima,
    vidaAtual: item.vidaAtual ?? item.vida_atual
  };
}

function obterAtributosPersonagem(personagem) {
  return [
    `FOR ${personagem.forca ?? "—"}`,
    `DES ${personagem.destreza ?? "—"}`,
    `CON ${personagem.constituicao ?? "—"}`,
    `INT ${personagem.inteligencia ?? "—"}`,
    `SAB ${personagem.sabedoria ?? "—"}`,
    `CAR ${personagem.carisma ?? "—"}`
  ].join(" • ");
}

async function inicializarSupabase() {
  if (!window.supabase) {
    throw new Error("Não foi possível carregar o serviço de autenticação.");
  }

  supabaseClient = window.supabase.createClient(
    SUPABASE_URL,
    SUPABASE_KEY
  );
}

async function obterJogadorAutenticado() {
  if (!supabaseClient) {
    await inicializarSupabase();
  }

  const {
    data: { session },
    error: sessionError
  } = await supabaseClient.auth.getSession();

  if (sessionError) {
    throw new Error("Não foi possível verificar sua sessão.");
  }

  if (!session) {
    window.location.href = "index.html";
    return null;
  }

  const {
    data: { user },
    error: userError
  } = await supabaseClient.auth.getUser();

  if (userError || !user) {
    window.location.href = "index.html";
    return null;
  }

  jogadorId = user.id;
  return jogadorId;
}

async function requisicaoApi(url, options = {}) {
  const resposta = await fetch(url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {})
    }
  });

  const texto = await resposta.text();
  let dados = null;

  if (texto) {
    try {
      dados = JSON.parse(texto);
    } catch {
      dados = texto;
    }
  }

  if (!resposta.ok) {
    const mensagem =
      dados?.erro ||
      dados?.message ||
      dados?.mensagem ||
      "Não foi possível concluir a operação.";

    throw new Error(mensagem);
  }

  return dados;
}

async function carregarPersonagens() {
  if (!jogadorId) {
    return;
  }

  try {
    const dados = await requisicaoApi(
      `/api/personagens?jogadorId=${encodeURIComponent(jogadorId)}`
    );

    personagens = Array.isArray(dados)
      ? dados.map(normalizarPersonagem)
      : [];

    renderCharacters();
  } catch (erro) {
    characterList.replaceChildren();

    const mensagem = document.createElement("p");
    mensagem.className = "empty-state";
    mensagem.textContent = erro.message;

    characterList.appendChild(mensagem);
  }
}

function openForm() {
  if (!catalogosCarregados) {
    alert("Aguarde o carregamento das raças e classes.");
    return;
  }

  characterFormSection.hidden = false;
  characterFormSection.scrollIntoView({
    behavior: "smooth",
    block: "start"
  });

  atualizarDetalhesRaca();
  atualizarDetalhesClasse();
  atualizarPrevia();
  characterNameInput.focus();
}

function closeForm() {
  characterForm.reset();
  characterFormSection.hidden = true;

  elemento("race-details").hidden = true;
  elemento("class-details").hidden = true;
  elemento("character-preview").hidden = true;

  saveButton.disabled = false;
  saveButton.textContent = "Salvar personagem";
  salvandoPersonagem = false;
}

function renderCharacters() {
  characterList.replaceChildren();

  if (personagens.length === 0) {
    const vazio = document.createElement("div");
    vazio.className = "empty-state";

    const titulo = document.createElement("h3");
    titulo.textContent = "Sua aventura começa aqui";

    const texto = document.createElement("p");
    texto.textContent =
      "Você ainda não criou personagens. Comece sua jornada criando seu primeiro aventureiro.";

    vazio.append(titulo, texto);
    characterList.appendChild(vazio);
    return;
  }

  personagens.forEach((character) => {
    const card = document.createElement("article");
    card.className = "character-card";
    card.tabIndex = 0;
    card.setAttribute("role", "button");
    card.setAttribute("aria-haspopup", "dialog");
    card.setAttribute("aria-label", `Ver detalhes de ${character.name}`);

    card.addEventListener("click", () => {
      abrirDetalhesPersonagem(character.id);
    });

    card.addEventListener("keydown", (event) => {
      if (event.target !== card) {
        return;
      }

      if (event.key === "Enter" || event.key === " ") {
        event.preventDefault();
        abrirDetalhesPersonagem(character.id);
      }
    });

    const symbol = document.createElement("div");
    symbol.className = "character-symbol";
    symbol.textContent = "✦";

    const title = document.createElement("h3");
    title.className = "character-title";
    title.textContent = character.name;

    const details = document.createElement("p");
    details.className = "character-details";
    details.textContent =
      `${character.race} • ${character.characterClass} • Nível ${character.level}`;

    const stats = document.createElement("p");
    stats.className = "character-stats";
    stats.textContent =
      `PV ${character.vidaAtual ?? "—"}/${character.vidaMaxima ?? "—"} • ` +
      obterAtributosPersonagem(character);

    const description = document.createElement("p");
    description.className = "character-description";
    description.textContent =
      character.backstory || "Uma nova história aguarda para ser escrita.";

    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "delete-button";
    deleteButton.textContent = "Excluir personagem";
    deleteButton.setAttribute("aria-label", `Excluir ${character.name}`);

    deleteButton.addEventListener("click", (event) => {
      event.stopPropagation();
      excluirPersonagem(character.id);
    });

    card.append(
      symbol,
      title,
      details,
      stats,
      description,
      deleteButton
    );

    characterList.appendChild(card);
  });
}

function abrirDetalhesPersonagem(id) {
  const personagem = personagens.find(
    (item) => String(item.id) === String(id)
  );

  if (!personagem) {
    alert("Não foi possível encontrar esta ficha.");
    return;
  }

  const raca = racasCatalogo.find(
    (item) => item.nome === personagem.race
  );

  const classe = classesCatalogo.find(
    (item) => item.nome === personagem.characterClass
  );

  const passiva = obterPassiva(raca);

  mostrarTexto("details-name", personagem.name);
  mostrarTexto(
    "details-identity",
    `${personagem.race} • ${personagem.characterClass}`
  );

  mostrarTexto(
    "details-hp",
    `${personagem.vidaAtual ?? "—"} / ${personagem.vidaMaxima ?? "—"}`
  );

  const defesa = personagem.destreza == null
    ? "—"
    : 10 + calcularModificador(Number(personagem.destreza));

  mostrarTexto("details-defense", defesa);
  mostrarTexto("details-energy", energiaDaClasse(personagem.characterClass));
  mostrarTexto("details-level", personagem.level ?? 1);

  mostrarAtributos("details-attributes", {
    forca: personagem.forca,
    destreza: personagem.destreza,
    constituicao: personagem.constituicao,
    inteligencia: personagem.inteligencia,
    sabedoria: personagem.sabedoria,
    carisma: personagem.carisma
  });

  mostrarTexto(
    "details-race-description",
    raca?.descricao,
    "Descrição da raça não cadastrada."
  );

  mostrarTexto(
    "details-passive-name",
    passiva.nome,
    "Passiva racial não cadastrada."
  );

  mostrarTexto(
    "details-passive-description",
    passiva.descricao,
    "Descrição da passiva não cadastrada."
  );

  mostrarTexto(
    "details-passive-condition",
    passiva.condicao ??
      passiva.condicaoAtivacao ??
      passiva.gatilho,
    "Consulte a descrição da passiva para saber quando ela se aplica."
  );

  mostrarTexto(
    "details-class-description",
    classe?.descricao,
    "Descrição da classe não cadastrada."
  );

  mostrarLista(
    "details-abilities",
    classe?.habilidadesIniciais,
    "Nenhuma habilidade inicial cadastrada."
  );

  const historia = personagem.backstory?.trim() || "";
  const historiaSection = elemento("details-backstory-section");

  historiaSection.hidden = !historia;
  mostrarTexto("details-backstory", historia);

  detailsModal.hidden = false;
  document.body.classList.add("modal-open");
  elemento("close-details-button").focus();
}

function fecharDetalhesPersonagem() {
  detailsModal.hidden = true;
  document.body.classList.remove("modal-open");
}

async function excluirPersonagem(id) {
  if (!confirm("Tem certeza de que deseja excluir este personagem?")) {
    return;
  }

  try {
    await requisicaoApi(
      `/api/personagens/${encodeURIComponent(id)}?jogadorId=${encodeURIComponent(jogadorId)}`,
      { method: "DELETE" }
    );

    personagens = personagens.filter(
      (personagem) => String(personagem.id) !== String(id)
    );

    renderCharacters();
  } catch (erro) {
    alert(`Não foi possível excluir o personagem: ${erro.message}`);
  }
}

async function carregarCatalogo(url, select, textoInicial, tipo) {
  select.replaceChildren();

  const opcaoInicial = document.createElement("option");
  opcaoInicial.value = "";
  opcaoInicial.textContent = textoInicial;
  select.appendChild(opcaoInicial);
  select.disabled = true;

  try {
    const items = await requisicaoApi(url);

    if (!Array.isArray(items)) {
      throw new Error("O catálogo retornou um formato inválido.");
    }

    if (tipo === "racas") {
      racasCatalogo = items;
    } else {
      classesCatalogo = items;
    }

    items.forEach((item) => {
      const option = document.createElement("option");
      option.value = item.nome;
      option.textContent = item.nome;
      select.appendChild(option);
    });

    return true;
  } catch (erro) {
    const opcaoErro = document.createElement("option");
    opcaoErro.value = "";
    opcaoErro.textContent = "Não foi possível carregar as opções";
    select.appendChild(opcaoErro);

    console.error(`Erro ao carregar ${tipo}:`, erro);
    return false;
  } finally {
    select.disabled = false;
  }
}

async function carregarCatalogos() {
  const resultados = await Promise.all([
    carregarCatalogo(
      "/api/catalogo/racas",
      raceSelect,
      "Escolha uma raça",
      "racas"
    ),
    carregarCatalogo(
      "/api/catalogo/classes",
      classSelect,
      "Escolha uma classe",
      "classes"
    )
  ]);

  catalogosCarregados = resultados.every(Boolean);

  if (catalogosCarregados) {
    atualizarDetalhesRaca();
    atualizarDetalhesClasse();
  }
}

createButton.addEventListener("click", openForm);
cancelButton.addEventListener("click", closeForm);
raceSelect.addEventListener("change", atualizarDetalhesRaca);
classSelect.addEventListener("change", atualizarDetalhesClasse);
characterNameInput.addEventListener("input", atualizarPrevia);

characterForm.addEventListener("submit", async (event) => {
  event.preventDefault();

  if (salvandoPersonagem) {
    return;
  }

  if (!catalogosCarregados) {
    alert("Não foi possível carregar as raças e classes.");
    return;
  }

  if (!jogadorId) {
    alert("Sua sessão expirou. Entre novamente.");
    window.location.href = "index.html";
    return;
  }

  const nome = characterNameInput.value.trim();
  const raca = raceSelect.value;
  const classe = classSelect.value;

  if (!nome || !raca || !classe) {
    alert("Preencha o nome, a raça e a classe do personagem.");
    return;
  }

  salvandoPersonagem = true;
  saveButton.disabled = true;
  saveButton.textContent = "Salvando...";

  try {
    const dados = await requisicaoApi("/api/personagens", {
      method: "POST",
      body: JSON.stringify({
        jogadorId,
        nome,
        classe,
        raca
      })
    });

    personagens.push(normalizarPersonagem(dados));
    renderCharacters();
    closeForm();
    alert("Personagem salvo com sucesso!");
  } catch (erro) {
    alert(`Não foi possível salvar o personagem: ${erro.message}`);
  } finally {
    salvandoPersonagem = false;
    saveButton.disabled = false;
    saveButton.textContent = "Salvar personagem";
  }
});

elemento("close-details-button").addEventListener(
  "click",
  fecharDetalhesPersonagem
);

elemento("close-details-footer").addEventListener(
  "click",
  fecharDetalhesPersonagem
);

detailsModal.addEventListener("click", (event) => {
  if (event.target === detailsModal) {
    fecharDetalhesPersonagem();
  }
});

document.addEventListener("keydown", (event) => {
  if (event.key === "Escape" && !detailsModal.hidden) {
    fecharDetalhesPersonagem();
  }
});

async function iniciarPagina() {
  createButton.disabled = true;

  try {
    await inicializarSupabase();
    const id = await obterJogadorAutenticado();

    if (!id) {
      return;
    }

    await carregarCatalogos();

    if (!catalogosCarregados) {
      alert("Não foi possível carregar as raças e classes. Verifique se o servidor está funcionando.");
    }

    await carregarPersonagens();
  } catch (erro) {
    console.error("Erro ao iniciar a página:", erro);
    alert(erro.message || "Não foi possível iniciar a página.");
  } finally {
    createButton.disabled = false;
  }
}

renderCharacters();
iniciarPagina();
