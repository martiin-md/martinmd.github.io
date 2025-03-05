const translations = {
    es: {
        "contact-title": "\xa1Pong\xe1monos en Contacto!",
        "contact-description": "Si tienes alguna consulta, duda o simplemente deseas charlar sobre futuros proyectos, no dudes en enviarme un mensaje. \xa1Estoy disponible para nuevas oportunidades!",
        "contact-name": "Nombre",
        "contact-email": "Correo Electr\xf3nico",
        "contact-message": "Mensaje",
        "contact-send": "Enviar",
        "contact-subtitle": "Tambi\xe9n puedes contactarme a trav\xe9s de mis redes sociales:",
        "menu-home": "Inicio",
        "menu-projects": "Proyectos",
        "menu-download-cv": "Descargar CV",
        "menu-contact": "Contacto"
    },
    en: {
        "contact-title": "Let's Get In Touch!",
        "contact-description": "If you have any questions, doubts, or just want to chat about future projects, feel free to send me a message. I'm available for new opportunities!",
        "contact-name": "Name",
        "contact-email": "Email",
        "contact-message": "Message",
        "contact-send": "Send",
        "contact-subtitle": "You can also reach out to me through my social media:",
        "menu-home": "Home",
        "menu-projects": "Projects",
        "menu-download-cv": "Download CV",
        "menu-contact": "Contact"
    }
};
function changeLanguage(lang) {
    if (!translations[lang]) {
        console.error(`Idioma "${lang}" no est\xe1 definido en translations`);
        return;
    }
    // Recorremos todos los elementos con las claves de traducción
    for(const id in translations[lang]){
        const element = document.getElementById(id);
        if (element) element.innerText = translations[lang][id]; // Cambia el texto de cada elemento
        else console.warn(`No se encontr\xf3 el elemento con id="${id}". Traducci\xf3n faltante.`);
    }
    // Cambiar el idioma en la etiqueta <html> para accesibilidad y SEO
    document.documentElement.lang = lang;
    // Guardar la preferencia de idioma en localStorage
    localStorage.setItem("language", lang);
}
document.addEventListener("DOMContentLoaded", function() {
    // Obtener el idioma guardado de localStorage
    const savedLang = localStorage.getItem("language") || "es"; // Por defecto, Español
    changeLanguage(savedLang);
});
// Si tienes botones para cambiar de idioma dinámicamente:
document.getElementById("change-to-es").addEventListener("click", function() {
    changeLanguage("es");
});
document.getElementById("change-to-en").addEventListener("click", function() {
    changeLanguage("en");
});

//# sourceMappingURL=index.d0b9957b.js.map
