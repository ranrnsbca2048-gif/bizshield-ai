
// =========================
// ROLE SELECTION
// =========================

const roleCards = document.querySelectorAll(".role-card");
const selectedRole = document.getElementById("selectedRole");

roleCards.forEach(card => {
    card.addEventListener("click", () => {

        // Remove active role
        roleCards.forEach(role => {
            role.classList.remove("active");
        });

        // Add active role
        card.classList.add("active");

        // Store selected role
        selectedRole.value = card.dataset.role;
    });
});


// =========================
// PASSWORD SHOW / HIDE
// =========================

const password = document.getElementById("password");
const passwordToggle = document.getElementById("passwordToggle");
const passwordIcon = passwordToggle.querySelector("i");

passwordToggle.addEventListener("click", () => {

    if (password.type === "password") {

        // Show password
        password.type = "text";

        // Change icon to eye-slash
        passwordIcon.classList.remove("fa-eye");
        passwordIcon.classList.add("fa-eye-slash");

        passwordToggle.setAttribute(
            "aria-label",
            "Hide password"
        );

    } else {

        // Hide password
        password.type = "password";

        // Change icon back to eye
        passwordIcon.classList.remove("fa-eye-slash");
        passwordIcon.classList.add("fa-eye");

        passwordToggle.setAttribute(
            "aria-label",
            "Show password"
        );
    }
});


// =========================
// LIGHT / DARK MODE
// =========================

const themeToggle = document.getElementById("themeToggle");


// Get saved theme
const savedTheme = localStorage.getItem("bizshield-theme");

if (savedTheme === "dark") {
    document.body.classList.add("dark-mode");
}


// Update theme toggle UI
function updateThemeToggle() {

    const isDarkMode =
        document.body.classList.contains("dark-mode");

    if (isDarkMode) {

        themeToggle.setAttribute(
            "aria-label",
            "Switch to light mode"
        );

    } else {

        themeToggle.setAttribute(
            "aria-label",
            "Switch to dark mode"
        );
    }
}


// Set correct UI when page loads
updateThemeToggle();


// Toggle theme
themeToggle.addEventListener("click", () => {

    document.body.classList.toggle("dark-mode");

    const isDarkMode =
        document.body.classList.contains("dark-mode");


    // Save theme preference
    localStorage.setItem(
        "bizshield-theme",
        isDarkMode ? "dark" : "light"
    );


    // Update toggle
    updateThemeToggle();
});
