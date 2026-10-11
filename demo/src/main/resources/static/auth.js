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

loginTab.addEventListener("click", () => {
    switchTab("login");
});

registerTab.addEventListener("click", () => {
    switchTab("register");
});

document.querySelectorAll("[data-switch]").forEach((button) => {
    button.addEventListener("click", () => {
        switchTab(button.dataset.switch);
    });
});

loginForm.addEventListener("submit", (event) => {
    event.preventDefault();

    if (!loginForm.reportValidity()) {
        return;
    }

    showFeedback(
        "Interface pronta! O login será conectado ao Supabase Auth na próxima etapa."
    );
});

registerForm.addEventListener("submit", (event) => {
    event.preventDefault();

    if (!registerForm.reportValidity()) {
        return;
    }

    const name = document.getElementById("register-name").value.trim();
    const password = document.getElementById("register-password").value;
    const confirmPassword = document.getElementById("register-confirm-password").value;

    if (name.length < 3) {
        showFeedback("O nome de aventureiro precisa ter pelo menos 3 caracteres.", "error");
        return;
    }

    if (password.length < 8) {
        showFeedback("A senha precisa ter pelo menos 8 caracteres.", "error");
        return;
    }

    if (password !== confirmPassword) {
        showFeedback("As senhas não coincidem. Verifique e tente novamente.", "error");
        return;
    }

    showFeedback(
        "Cadastro validado! A criação da conta será conectada ao Supabase Auth na próxima etapa.",
        "success"
    );
});

forgotPasswordButton.addEventListener("click", () => {
    showFeedback(
        "A recuperação de senha será disponibilizada quando o Supabase Auth for integrado."
    );
});

switchTab("login");