
const createButton = document.getElementById("create-button");
const cancelButton = document.getElementById("cancel-button");
const formSection = document.getElementById("character-form-section");
const form = document.getElementById("character-form");
const characterList = document.getElementById("character-list");
const raceSelect = document.getElementById("character-race");
const classSelect = document.getElementById("character-class");

const playerName = sessionStorage.getItem("mythboundPlayerName");
const storageKey = "mythboundCharacters";

let characters = loadCharacters();
let catalogosCarregados = false;

function loadCharacters() {
    try {
        const saved = JSON.parse(
            localStorage.getItem(storageKey) || "[]"
        );

        return Array.isArray(saved) ? saved : [];
    } catch {
        return [];
    }
}

function saveCharacters() {
    localStorage.setItem(storageKey, JSON.stringify(characters));
}

function openForm() {
    formSection.hidden = false;

    formSection.scrollIntoView({
        behavior: "smooth",
        block: "start"
    });

    document.getElementById("character-name").focus();
}

function closeForm() {
    form.reset();
    formSection.hidden = true;
}

function renderCharacters() {
    characterList.replaceChildren();

    if (characters.length === 0) {
        const emptyCard = document.createElement("article");
        emptyCard.className = "empty-card";

        const symbol = document.createElement("div");
        symbol.className = "empty-symbol";
        symbol.textContent = "✧";

        const title = document.createElement("h3");
        title.textContent = "Nenhuma lenda registrada";

        const description = document.createElement("p");
        description.textContent =
            "Seu caminho ainda está em branco. Crie seu primeiro personagem para começar.";

        const button = document.createElement("button");
        button.type = "button";
        button.className = "secondary-button";
        button.textContent = "Criar meu primeiro personagem";
        button.addEventListener("click", openForm);

        emptyCard.append(symbol, title, description, button);
        characterList.appendChild(emptyCard);
        return;
    }

    characters.forEach(character => {
        const card = document.createElement("article");
        card.className = "character-card";

        const symbol = document.createElement("div");
        symbol.className = "character-symbol";
        symbol.textContent = "✧";

        const title = document.createElement("h3");
        title.textContent = character.name;

        const details = document.createElement("p");
        details.className = "character-details";
        details.textContent =
            `${character.race} • ${character.characterClass} • Nível ${character.level}`;

        const description = document.createElement("p");
        description.className = "character-description";
        description.textContent =
            character.backstory ||
            "Uma nova história está prestes a começar.";

        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.className = "secondary-button";
        deleteButton.textContent = "Excluir personagem";

        deleteButton.addEventListener("click", () => {
            if (!confirm(`Deseja excluir ${character.name}?`)) {
                return;
            }

            characters = characters.filter(
                item => item.id !== character.id
            );

            saveCharacters();
            renderCharacters();
        });

        card.append(symbol, title, details, description, deleteButton);
        characterList.appendChild(card);
    });
}

async function carregarCatalogo(url, select, textoInicial) {
    select.disabled = true;
    select.replaceChildren();

    const loadingOption = document.createElement("option");
    loadingOption.value = "";
    loadingOption.textContent = "Carregando...";
    select.appendChild(loadingOption);

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`Erro HTTP ${response.status}`);
        }

        const items = await response.json();

        if (!Array.isArray(items)) {
            throw new Error("O backend não retornou uma lista.");
        }

        select.replaceChildren();

        const initialOption = document.createElement("option");
        initialOption.value = "";
        initialOption.textContent = textoInicial;
        select.appendChild(initialOption);

        items.forEach(item => {
            if (!item.nome) {
                return;
            }

            const option = document.createElement("option");
            option.value = item.nome;
            option.textContent = item.nome;
            select.appendChild(option);
        });

        if (items.length === 0) {
            initialOption.textContent = "Nenhuma opção cadastrada";
        }
    } catch (error) {
        console.error(`Erro ao carregar ${url}:`, error);

        select.replaceChildren();

        const errorOption = document.createElement("option");
        errorOption.value = "";
        errorOption.textContent = "Erro ao carregar opções";
        select.appendChild(errorOption);
    } finally {
        select.disabled = false;
    }
}

async function carregarCatalogos() {
    await Promise.all([
        carregarCatalogo(
            "/api/catalogo/racas",
            raceSelect,
            "Escolha uma raça"
        ),
        carregarCatalogo(
            "/api/catalogo/classes",
            classSelect,
            "Escolha uma classe"
        )
    ]);

    catalogosCarregados = true;
}

createButton.addEventListener("click", openForm);
cancelButton.addEventListener("click", closeForm);

form.addEventListener("submit", event => {
    event.preventDefault();

    if (!catalogosCarregados) {
        alert("Aguarde o carregamento das raças e classes.");
        return;
    }

    const character = {
        id: crypto.randomUUID(),
        ownerName: playerName || "Aventureiro",
        name: document.getElementById("character-name").value.trim(),
        race: raceSelect.value,
        characterClass: classSelect.value,
        level: 1,
        backstory: document.getElementById("character-backstory").value.trim()
    };

    if (!character.name) {
        alert("Informe o nome do personagem.");
        return;
    }

    if (!character.race || !character.characterClass) {
        alert("Escolha uma raça e uma classe.");
        return;
    }

    characters.push(character);
    saveCharacters();
    renderCharacters();
    closeForm();
});

renderCharacters();
carregarCatalogos();
