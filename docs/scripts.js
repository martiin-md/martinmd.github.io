/* =========================================================
   MARTIN DIMITROV PORTFOLIO - MAIN SCRIPT
   ========================================================= */

/* =========================
   ENLACES IMPORTANTES
========================= */

const LINKS = {
  github: "https://github.com/martiin-md",
  githubPortfolio: "https://github.com/martiin-md/martinmd.github.io",
  linkedin: "https://www.linkedin.com/in/martin-miroslavov-dimitrov/",
  instagram: "https://www.instagram.com/martiin.md/",
  email: "mailto:miroslavovdimitrovmartin@gmail.com",
  cv: "./Martin Miroslavov Dimitrov CV.pdf",

  projects: {
    viewVeppora: "https://github.com/martiin-md/ViewVeppora",
    eventsIbiza: "https://github.com/martiin-md",
    breakout: "https://github.com/martiin-md",
    musicManager: "https://github.com/martiin-md",
    chatbot: "https://appinventor.mit.edu/",
  }
};

/* =========================================================
   TRADUCCIONES
   ========================================================= */

const translations = {
  es: {
    /* NAVBAR */
    "nav-home": "Inicio",
    "nav-about": "Sobre mí",
    "nav-projects": "Proyectos",
    "nav-contact": "Contacto",
    "nav-skills": "Tecnologías",
    "language-button": "Idioma",

    /* MENÚ */
    "menu-contactar": "Contactar",
    "menu-download-cv": "Descargar CV",
    "menu-home": "Inicio",
    "menu-projects": "Proyectos",
    "menu-contact": "Contacto",

    /* HERO */
    "hero-title":
      "DESARROLLADOR ANDROID UI/UX & BACKEND",

    "hero-description":
      "Creo aplicaciones modernas, rápidas y enfocadas en experiencia de usuario. Especializado en Android con Java y Kotlin, diseño UI/UX y soluciones backend.",

    "hero-btn-projects": "Ver proyectos",
    "hero-btn-contact": "Contactar",

    job: "Programador Mobile | Técnico de Redes | Diseñador UI/UX | Freelancer",

    entrepreneur: "Joven Emprendedor",

    /* ABOUT */
    "about-title": "Sobre mí",

    "about-description":
      "Soy un Programador y experto en Trading apasionado por la tecnología y el desarrollo de aplicaciones móviles. Tengo experiencia en Android con Java y Kotlin, diseño UI/UX, backend y desarrollo web moderno.",

    "skills-title": "Habilidades",

    "skill-1":
      "Desarrollo de aplicaciones móviles (Android, Java, Kotlin)",

    "skill-2": "Diseño UI/UX moderno",

    "skill-3":
      "Desarrollo backend (Firebase, Node.js)",

    "skill-4":
      "Bases de datos SQL, SQLite y MongoDB",

    "skill-5":
      "Trabajo remoto y metodologías ágiles",

    /* PROJECTS */
    "projects-title": "Proyectos Destacados",

    "projects-description":
      "Algunos de los proyectos desarrollados en Android, automatización, videojuegos y plataformas digitales.",

    "toggle-projects": "▼ Ver más proyectos",

    "project1-title": "Gestión de Eventos Ibiza",

    "project1-description":
      "Aplicación Android para gestionar eventos, artistas y reservas utilizando Java y SQLite.",

    "project2-title": "Juego Breakout",

    "project2-description":
      "Videojuego arcade estilo Breakout desarrollado con Godot 4.3 para Android.",

    "project3-title": "Music Management App",

    "project3-description":
      "Aplicación para gestión de música, listas y canciones usando Firebase.",

    "project4-title": "IA ChatBot App",

    "project4-description":
      "Aplicación de Inteligencia Artificial desarrollada en MIT App Inventor.",

    "project5-title": "Controlador de Música",

    "project5-description":
      "Sistema para gestionar controles musicales y automatización.",

    "project6-title": "Gestión Concesionario",

    "project6-description":
      "Sistema Java + SQL para gestionar vehículos, clientes y ventas.",

    "project7-title": "View Veppora",

    "project7-description":
      "Aplicación financiera con simuladores, educación y análisis de mercado en tiempo real.",

    /* LANGUAGES */
    "languages-title":
      "Lenguajes de Programación",

    "languages-description":
      "Tecnologías y herramientas con las que trabajo habitualmente.",

    /* CONTACT */
    "contact-title":
      "¡Pongámonos en Contacto!",

    "contact-description":
      "Si tienes alguna consulta o deseas colaborar en un proyecto, puedes escribirme directamente.",

    "contact-name": "Nombre",

    "contact-email":
      "Correo Electrónico",

    "contact-message": "Mensaje",

    "contact-send": "Enviar",

    "contact-subtitle":
      "También puedes contactarme a través de mis redes sociales:",

    /* FOOTER */
    "footer-connect-title":
      "Conéctate conmigo",

    "footer-connect-description":
      "¡Me encantaría saber de ti! Puedes encontrarme en mis redes sociales.",

    "footer-note":
      "© 2026 Martin Miroslavov Dimitrov. Todos los derechos reservados."
  },

  en: {
    /* NAVBAR */
    "nav-home": "Home",
    "nav-about": "About",
    "nav-projects": "Projects",
    "nav-contact": "Contact",
    "nav-skills": "Technologies",
    "language-button": "Language",

    /* MENÚ */
    "menu-contactar": "Contact",
    "menu-download-cv": "Download CV",
    "menu-home": "Home",
    "menu-projects": "Projects",
    "menu-contact": "Contact",

    /* HERO */
    "hero-title":
      "ANDROID DEVELOPER UI/UX & BACKEND",

    "hero-description":
      "I create modern, fast and user-focused applications. Specialized in Android development with Java and Kotlin, UI/UX and backend solutions.",

    "hero-btn-projects": "View Projects",
    "hero-btn-contact": "Contact",

    job:
      "Mobile Developer | Network Technician | UI/UX Designer | Freelancer",

    entrepreneur: "Young Entrepreneur",

    /* ABOUT */
    "about-title": "About Me",

    "about-description":
      "I am a Programmer and Trader passionate about technology and mobile application development. Experienced in Android with Java and Kotlin, UI/UX design, backend and modern web development.",

    "skills-title": "Skills",

    "skill-1":
      "Mobile App Development (Android, Java, Kotlin)",

    "skill-2": "Modern UI/UX Design",

    "skill-3":
      "Backend Development (Firebase, Node.js)",

    "skill-4":
      "SQL, SQLite and MongoDB Databases",

    "skill-5":
      "Remote work and agile methodologies",

    /* PROJECTS */
    "projects-title":
      "Featured Projects",

    "projects-description":
      "Some projects developed in Android, automation, videogames and digital platforms.",

    "toggle-projects":
      "▼ View more projects",

    "project1-title":
      "Ibiza Events Management",

    "project1-description":
      "Android app to manage events, artists and bookings using Java and SQLite.",

    "project2-title":
      "Breakout Game",

    "project2-description":
      "Breakout arcade game developed with Godot 4.3 for Android.",

    "project3-title":
      "Music Management App",

    "project3-description":
      "Music and playlists management app using Firebase.",

    "project4-title":
      "IA ChatBot App",

    "project4-description":
      "Artificial Intelligence app developed with MIT App Inventor.",

    "project5-title":
      "Music Controller",

    "project5-description":
      "Music controller and automation system.",

    "project6-title":
      "Dealership Management",

    "project6-description":
      "Java + SQL system to manage vehicles, customers and sales.",

    "project7-title":
      "View Veppora",

    "project7-description":
      "Financial investment app with simulators, education and market analysis.",

    /* LANGUAGES */
    "languages-title":
      "Programming Languages",

    "languages-description":
      "Technologies and tools I usually work with.",

    /* CONTACT */
    "contact-title":
      "Let's Get In Touch!",

    "contact-description":
      "If you have any questions or want to collaborate on a project, feel free to contact me.",

    "contact-name": "Name",

    "contact-email": "Email",

    "contact-message": "Message",

    "contact-send": "Send",

    "contact-subtitle":
      "You can also reach me through my social media:",

    /* FOOTER */
    "footer-connect-title":
      "Connect with Me",

    "footer-connect-description":
      "I would love to hear from you! Find me on my social networks.",

    "footer-note":
      "© 2026 Martin Miroslavov Dimitrov. All rights reserved."
  }
};

/* =========================================================
   CAMBIO DE IDIOMA
   ========================================================= */

function changeLanguage(lang) {
  if (!translations[lang]) return;

  localStorage.setItem("selectedLanguage", lang);

  for (const id in translations[lang]) {
    const element = document.getElementById(id);

    if (element) {
      element.innerText = translations[lang][id];
    }
  }
}

/* =========================================================
   CARGA INICIAL
   ========================================================= */

document.addEventListener("DOMContentLoaded", () => {

  /* IDIOMA */
  const savedLanguage =
    localStorage.getItem("selectedLanguage") || "es";

  changeLanguage(savedLanguage);

  /* SCROLL ANIMATIONS */
  initScrollAnimations();

  /* LINKS AUTOMÁTICOS */
  initLinks();

  /* CARRUSEL */
  initCarousel();

  /* SCROLL NAVBAR */
  initNavbarEffect();
});

/* =========================================================
   ANIMACIONES SCROLL
   ========================================================= */

function initScrollAnimations() {

  const elements = document.querySelectorAll(
    ".scroll-effect, .about-card, .project-card, .skill-box, .contact-box"
  );

  function isVisible(el) {
    const rect = el.getBoundingClientRect();

    return (
      rect.top <= window.innerHeight - 100 &&
      rect.bottom >= 0
    );
  }

  function handleScroll() {
    elements.forEach((element) => {
      if (isVisible(element)) {
        element.classList.add("visible");
        element.classList.add("show");
      }
    });
  }

  window.addEventListener("scroll", handleScroll);

  handleScroll();
}

/* =========================================================
   LINKS AUTOMÁTICOS
   ========================================================= */

function initLinks() {

  /* GITHUB */
  document.querySelectorAll(".github-link")
    .forEach(el => {
      el.href = LINKS.github;
      el.target = "_blank";
    });

  /* LINKEDIN */
  document.querySelectorAll(".linkedin-link")
    .forEach(el => {
      el.href = LINKS.linkedin;
      el.target = "_blank";
    });

  /* INSTAGRAM */
  document.querySelectorAll(".instagram-link")
    .forEach(el => {
      el.href = LINKS.instagram;
      el.target = "_blank";
    });

  /* EMAIL */
  document.querySelectorAll(".email-link")
    .forEach(el => {
      el.href = LINKS.email;
    });

  /* CV */
  document.querySelectorAll(".cv-link")
    .forEach(el => {
      el.href = LINKS.cv;
      el.target = "_blank";
    });

  /* PROYECTOS */
  const veppora = document.getElementById("view-veppora-link");

  if (veppora) {
    veppora.href = LINKS.projects.viewVeppora;
    veppora.target = "_blank";
  }
}

/* =========================================================
   NAVBAR EFECTO
   ========================================================= */

function initNavbarEffect() {

  const navbar = document.querySelector(".navbar");

  if (!navbar) return;

  window.addEventListener("scroll", () => {

    if (window.scrollY > 40) {

      navbar.style.background =
        "rgba(15, 23, 42, 0.95)";

      navbar.style.backdropFilter =
        "blur(18px)";

    } else {

      navbar.style.background =
        "rgba(15, 23, 42, 0.75)";
    }
  });
}

/* =========================================================
   CARRUSEL PROYECTOS
   ========================================================= */

function initCarousel() {

  const carousel =
    document.querySelector(".carousel");

  if (!carousel) return;

  const dots =
    document.querySelectorAll(".dot");

  const projectWidth = 320;

  window.goToProject = function(index) {

    carousel.scrollTo({
      left: index * projectWidth,
      behavior: "smooth"
    });

    updateDots(index);
  };

  function updateDots(activeIndex) {

    dots.forEach((dot, index) => {

      dot.classList.toggle(
        "active",
        index === activeIndex
      );
    });
  }

  carousel.addEventListener("scroll", () => {

    const index = Math.round(
      carousel.scrollLeft / projectWidth
    );

    updateDots(index);
  });
}

/* =========================================================
   BOTÓN SCROLL TOP
   ========================================================= */

const scrollTopBtn =
  document.getElementById("scrollTopBtn");

if (scrollTopBtn) {

  window.addEventListener("scroll", () => {

    if (window.scrollY > 400) {

      scrollTopBtn.classList.add("active");

    } else {

      scrollTopBtn.classList.remove("active");
    }
  });

  scrollTopBtn.addEventListener("click", () => {

    window.scrollTo({
      top: 0,
      behavior: "smooth"
    });
  });
}

/* =========================================================
   FORMULARIO CONTACTO
   ========================================================= */

const contactForm =
  document.getElementById("contactForm");

if (contactForm) {

  contactForm.addEventListener("submit", () => {

    const button =
      contactForm.querySelector("button");

    if (button) {

      button.innerHTML =
        '<i class="fa-solid fa-spinner fa-spin"></i> Enviando...';
    }
  });
}

/* =========================================================
   EFECTO TYPEWRITER
   ========================================================= */

function typeWriterEffect(
  elementId,
  text,
  speed = 80
) {

  const element =
    document.getElementById(elementId);

  if (!element) return;

  let i = 0;

  element.innerHTML = "";

  function typing() {

    if (i < text.length) {

      element.innerHTML += text.charAt(i);

      i++;

      setTimeout(typing, speed);
    }
  }

  typing();
}

/* =========================================================
   UTILIDADES
   ========================================================= */

function openLink(url) {
  window.open(url, "_blank");
}

function sendEmail() {
  window.location.href = LINKS.email;
}

console.log(`
=========================================
 MARTIN DIMITROV PORTFOLIO
=========================================
 Portfolio cargado correctamente.
=========================================
`);