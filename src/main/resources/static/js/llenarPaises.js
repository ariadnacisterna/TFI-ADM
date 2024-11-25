windowonlad=obtenerPaises();
async function obtenerPaises() {
    try {
        const buscarpaises = await fetch('http://localhost:8080/api/adm/paises', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Parsear la respuesta como JSON
        const paises = await buscarpaises.json();
        llenarPaises(paises);
        // Llenar el combo box con los países


    } catch (error) {
        console.error('Error al obtener los países:', error);
    }
}
async function obtenerProvincias(paisId) {
    try {
        // Verifica que se haya seleccionado un país
        if (!paisId) {
            console.log('No se ha seleccionado un país.');
            return;
        }

        // Llamada a la API para obtener las provincias del país seleccionado
        const buscarProvincias = await fetch(`http://localhost:8080/api/adm/provincias/por-pais/${paisId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Parsear la respuesta como JSON
        const provincias = await buscarProvincias.json();
        console.log(provincias); // Verifica qué se recibe
        llenarProvincias(provincias);



    } catch (error) {
        console.error('Error al obtener las provincias:', error);
    }
}
async function obtenerLocalidades(provinciaId) {
    try {
        // Verifica que se haya seleccionado una provincia
        if (!provinciaId) {
            console.log('No se ha seleccionado una provincia.');
            return;
        }

        // Verifica en consola que provinciaId no sea undefined ni vacío
        console.log('Provincia ID antes de la solicitud:', provinciaId);

        // Llamada a la API para obtener las localidades de la provincia seleccionada
        const buscarLocalidades = await fetch(`http://localhost:8080/api/adm/localidades/por-provincia/${provinciaId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        // Verifica si la respuesta es exitosa
        if (!buscarLocalidades.ok) {
            throw new Error(`Error al obtener localidades: ${buscarLocalidades.statusText}`);
        }

        // Parsear la respuesta como JSON
        const localidades = await buscarLocalidades.json();
        console.log('Localidades obtenidas:', localidades);
        llenarLocalidades(localidades);// Verifica qué se recibe



    } catch (error) {
        console.error('Error al obtener las localidades:', error);
    }
}






 function llenarPaises(paises)
{
    const paisSelect = document.getElementById('pais');
    paisSelect.innerHTML = '<option value="">Seleccione un país</option>'; // Limpiar opciones existentes

    // Añadir las opciones de países
    paises.forEach(pais => {
        const option = document.createElement('option');
        option.value = pais.idpais; // Asignar el ID del país (idpais)
        option.textContent = pais.nombre; // Asignar el nombre del país
        paisSelect.appendChild(option);
    });

    // Agregar el evento para cargar provincias cuando se seleccione un país
    paisSelect.addEventListener('change', function() {
        const paisId = paisSelect.value; // Obtener el id del país seleccionado
        console.log('País seleccionado ID:', paisId); // Mostrar en consola el ID
        if (paisId) {
            obtenerProvincias(paisId); // Llamar a la función para cargar las provincias
        } else {
            console.log('No se ha seleccionado un país');
        }
    });
}

function llenarProvincias(provincias)
{
    // Llenar el combo box con las provincias
    const provinciaSelect = document.getElementById('provincia');
    provinciaSelect.innerHTML = '<option value="">Seleccione una provincia</option>'; // Limpiar opciones existentes

    // Añadir las opciones de provincias
    provincias.forEach(provincia => {
        const option = document.createElement('option');
        option.value = provincia.idprovincia; // Suponiendo que cada provincia tiene un campo 'id'
        option.textContent = provincia.nombre; // Suponiendo que cada provincia tiene un campo 'nombre'
        provinciaSelect.appendChild(option);
    });


    // Agregar el evento para cargar localidades cuando se seleccione una provincia
    provinciaSelect.addEventListener('change', function() {
        const provinciaId = provinciaSelect.value; // Obtener el id de la provincia seleccionada
        console.log('Provincia seleccionada ID:', provinciaId); // Mostrar en consola el ID
        if (provinciaId) {
            obtenerLocalidades(provinciaId); // Llamar a la función para cargar las localidades
        } else {
            console.log('No se ha seleccionado una provincia');
        }
    });

}
function llenarLocalidades(localidades)
{
    // Llenar el combo box con las localidades
    const localidadSelect = document.getElementById('localidad');
    localidadSelect.innerHTML = '<option value="">Seleccione una localidad</option>'; // Limpiar opciones existentes

    // Añadir las opciones de localidades
    localidades.forEach(localidad => {
        const option = document.createElement('option');
        option.value = localidad.idlocalidad; // Suponiendo que cada localidad tiene un campo 'idlocalidad'
        option.textContent = localidad.nombre; // Suponiendo que cada localidad tiene un campo 'nombre'
        localidadSelect.appendChild(option);
    });
}