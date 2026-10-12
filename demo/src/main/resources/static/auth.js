const SUPABASE_URL = "https://leummipycreyhzvmoope.supabase.co";
const SUPABASE_PUBLISHABLE_KEY = "sb_publishable_P5hdzuDrEfBiMQYjiCC0Pg_zhJvgH1B";

const supabaseClient = window.supabase.createClient(
    SUPABASE_URL,
    SUPABASE_PUBLISHABLE_KEY
);

const loginTab = document.getElementById("login-tab");
const registerTab = document.getElementById("register-tab");
const loginPanel = document.getElementById("login-panel");
const registerPanel = document.getElementById("register-panel");
const loginForm = document.getElementById("login-form");
const registerForm = document.getElementById("register-form");
const feedback = document.getElementById("feedback");
const forgotPasswordButton = document.getElementById("forgot-password");

function clearFeedback() {
    feedback.textContent = "";
    feedback.className = "feedback";
    feedback.hidden = true;
}

function showFeedback(message, type = "") {
    feedback.textContent = message;
    feedback.className = `feedback ${type}`.trim();
    feedback.hidden = false;
}

function switchTab(tab) {
    const showLogin = tab === "login";

    loginTab.classList.toggle("active", showLogin);
    registerTab.classList.toggle("active", !showLogin);

    loginTab.setAttribute("aria-selected", String(showLogin));
    registerTab.setAttribute("aria-selected", String(!showLogin));

    loginPanel.hidden = !showLogin;
    registerPanel.hidden = showLogin;

    loginPanel.classList.toggle("hidden", !showLogin);
    registerPanel.classList.toggle("hidden", showLogin);

    clearFeedback();
}

loginTab.addEventListener("click", () => switchTab("login"));
registerTab.addEventListener("click", () => switchTab("register"));

document.querySelectorAll("[data-switch]").forEach((button) => {
    button.addEventListener("click", () => {
        switchTab(button.dataset.switch);
    });
});

loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    if (!loginForm.reportValidity()) return;

    const email = document.getElementById("login-email").value.trim();
    const password = document.getElementById("login-password").value;

    const submitButton = loginForm.querySelector('[type="submit"]');

    if (submitButton) submitButton.disabled = true;

    showFeedback("Entrando no mundo de MYTHBOUND...", "");

    try {
        const { data, error } = await supabaseClient.auth.signInWithPassword({
            email,
            password
        });

        if (error) throw error;

        if (!data.session || !data.user) {
            throw new Error("O login não criou uma sessão válida.");
        }

        const displayName =
            data.user.user_metadata?.display_name ||
            data.user.email?.split("@")[0] ||
            "Aventureiro";

        sessionStorage.setItem("mythboundPlayerName", displayName);

        console.log("Login realizado com sucesso.");
        console.log("ID do usuário:", data.user.id);
        console.log("Sessão criada:", Boolean(data.session));

        window.location.href = new URL("menu.html", window.location.href).href;
    } catch (error) {
        console.error("Erro no login:", error);

        showFeedback(
            "Não foi possível entrar. Confira o e-mail e a senha e tente novamente.",
            "error"
        );
    } finally {
        if (submitButton) submitButton.disabled = false;
    }
});

registerForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    if (!registerForm.reportValidity()) return;

    const name = document.getElementById("register-name").value.trim();
    const email = document.getElementById("register-email").value.trim();
    const password = document.getElementById("register-password").value;
    const confirmPassword =
        document.getElementById("register-confirm-password").value;

    if (name.length < 3) {
        showFeedback("O nome precisa ter pelo menos 3 caracteres.", "error");
        return;
    }

    if (password.length < 8) {
        showFeedback("A senha precisa ter pelo menos 8 caracteres.", "error");
        return;
    }

    if (password !== confirmPassword) {
        showFeedback("As senhas não coincidem.", "error");
        return;
    }

    const submitButton = registerForm.querySelector('[type="submit"]');

    if (submitButton) submitButton.disabled = true;

    showFeedback("Criando sua conta...", "");

    try {
        const { data, error } = await supabaseClient.auth.signUp({
            email,
            password,
            options: {
                data: {
                    display_name: name
                }
            }
        });

        if (error) throw error;

        if (data.session && data.user) {
            sessionStorage.setItem("mythboundPlayerName", name);

            showFeedback(
                "Conta criada! Entrando no MYTHBOUND...",
                "success"
            );

            window.location.href = "/menu.html";
        } else {
            switchTab("login");

            showFeedback(
                "Cadastro realizado! Confira seu e-mail para confirmar a conta antes de fazer login.",
                "success"
            );
        }
    } catch (error) {
        console.error("Erro no cadastro:", error);

        showFeedback(
            "Não foi possível criar a conta. Verifique o e-mail e se ele já está cadastrado.",
            "error"
        );
    } finally {
        if (submitButton) submitButton.disabled = false;
    }
});

forgotPasswordButton.addEventListener("click", async () => {
    const emailInput = document.getElementById("login-email");
    const email = emailInput.value.trim();

    if (!email) {
        showFeedback(
            "Digite seu e-mail no campo de login antes de solicitar a recuperação.",
            "error"
        );

        emailInput.focus();
        return;
    }

    try {
        const { error } = await supabaseClient.auth.resetPasswordForEmail(
            email,
            {
                redirectTo: `${window.location.origin}/index.html`
            }
        );

        if (error) throw error;

        showFeedback(
            "Se houver uma conta para esse e-mail, você receberá instruções de recuperação.",
            "success"
        );
    } catch (error) {
        console.error("Erro na recuperação de senha:", error);

        showFeedback(
            "Não foi possível solicitar a recuperação de senha. Tente novamente.",
            "error"
        );
    }
});

switchTab("login");