async function cargarPaises() {
    try {
        const buscarpaises = await fetch('http://localhost:8080/api/adm/paises', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });


        // Parsear la respuesta como JSON
        const paises = await buscarpaises.json();

        // Mostrar los países en la consola para verificar
        console.log(paises);

        // Llenar el combo box con los países
        const paisSelect = document.getElementById('pais');
        paisSelect.innerHTML = '<option value="">Seleccione un país</option>'; // Limpiar opciones existentes

        // Añadir las opciones de países
        paises.forEach(pais => {
            const option = document.createElement('option');
            option.value = pais.id; // Suponiendo que el país tiene un campo 'id'
            option.textContent = pais.nombre; // Suponiendo que el país tiene un campo 'nombre'
            paisSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error al obtener los países:', error);
    }
}

// Llamar a la función cuando la página cargue
window.onload = cargarPaises;
