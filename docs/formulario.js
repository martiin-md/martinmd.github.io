document
  .getElementById("contactForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // Evitar que el formulario se envíe de manera predeterminada

    // Obtener los valores del formulario
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const message = document.getElementById("message").value;

    // Validación simple
    if (name && email && message) {
      fetch("https://formspree.io/f/xldgqnlb", {
        method: "POST",
        body: new FormData(this),
      })
        .then((response) => response.json())
        .then((data) => {
          alert("Formulario enviado correctamente");
          document.getElementById("contactForm").reset();
        })
        .catch((error) => {
          alert("Hubo un problema al enviar el formulario. Intenta de nuevo.");
        });
    } else {
      alert("Por favor, completa todos los campos.");
    }
  });
