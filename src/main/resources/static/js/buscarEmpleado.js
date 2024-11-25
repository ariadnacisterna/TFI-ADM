// Función para mostrar los empleados en la tabla.
windowonlad=funcionIniciar();
{
    window.onload = function() {
        // Ejecutamos ambas solicitudes en paralelo usando Promise.all
        Promise.all([
            mostrarEmpleadosEnTabla(),
            obtenerPaises()
        ])
            .then(([empleados, paises]) => {
                // Aquí puedes trabajar con los datos de ambos
                console.log("Empleados:", empleados);
                console.log("Paises:", paises);
            })
            .catch(error => {
                console.error("Error al obtener datos:", error);
            });
    }

}
function mostrarEmpleadosEnTabla(empleados) {
    const tbody = document.getElementById('empleados-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (empleados.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron empleados.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }
    // Recorrer los empleados y agregar las filas a la tabla
        empleados.forEach(empleado => {
        const row = document.createElement('tr');

        const nombreCell = document.createElement('td');
        nombreCell.textContent = empleado.nombre_completo;
        row.appendChild(nombreCell);
        const direccionCell = document.createElement('td');
        direccionCell.textContent = empleado.direccion;
        row.appendChild(direccionCell);
        const emailCell = document.createElement('td');
        emailCell.textContent = empleado.mail;
        row.appendChild(emailCell);
        const cuilCell = document.createElement('td');
        cuilCell.textContent = empleado.cuil;
        row.appendChild(cuilCell);
        const localidadCell = document.createElement('td');
        localidadCell.textContent = empleado.localidad_id.nombre;
        row.appendChild(localidadCell);
        // Columna para los botones de acción
        const accionesCell = document.createElement('td');
        // Botón de modificar con icono de Material Icons
        const verDetallesBtn = document.createElement('button');
        verDetallesBtn.className = 'btn btn-info btn-sm me-2';
        verDetallesBtn.innerHTML = '<span class="material-icons">visibility</span>'; // Icono de ver detalles
        verDetallesBtn.onclick = () =>buscarEmpleado(empleado.cuil);
        accionesCell.appendChild(verDetallesBtn);
        row.appendChild(accionesCell);
        tbody.appendChild(row);
    });
}


async function mostrarEmpleadoEnFormulario(empleado){

    document.getElementById('cuil2').value = String(empleado.cuil);
    document.getElementById('nombre').value = empleado.nombre_completo;
    document.getElementById('email').value=empleado.mail;
    document.getElementById('direccion').value=empleado.direccion;
    document.getElementById('fecha-nacimiento').value=empleado.fecha_nacimiento;
    document.getElementById('borrado').value=empleado.borrado;
    document.getElementById('localidad').value=empleado.localidad_id;

}

// Cargar todos los empleados al iniciar la página
async function cargarEmpleados() {
    try {
        // Enviar la solicitud GET para obtener todos los clientes
        const response = await fetch('http://localhost:8080/api/adm/empleados', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const empleados = await response.json();
            console.log('Empleados cargados:', empleados);
            mostrarEmpleadosEnTabla(empleados); // Llamar a la función para mostrar todos los empleados
        } else {
            const errorData = await response.json();
            alert('Error: ' + (errorData.message || 'Hubo un error al cargar los empleados.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}
async function buscarEmpleado(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/empleados/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('empleado no encontrado');
        }

        const empleadoBuscado = await response.json();

        // Convertir la respuesta a un arreglo para usar la misma función de mostrar
        mostrarEmpleadosEnTabla([empleadoBuscado]); // Llamamos la función para mostrar el empleado
        mostrarEmpleadoEnFormulario(empleadoBuscado);

    } catch (error) {
        console.error('Error al buscar empleado:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}

async function eliminarEmpleado(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/empleados/${cuil}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            alert('Empleado eliminado exitosamente!');  // Mensaje de éxito
            cargarEmpleados();
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta en caso de error
            alert('Error: ' + (errorData.message || 'Hubo un error al eliminar el empleado.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}
    // necesito cargar las provincias en el formulario a la hora de mostrar el empleado.



window.onload = cargarEmpleados;

// Función para obtener los datos del formulario (Cuil)
function obtenerDatosBusqueda() {
    const cuil = document.getElementById('cuil').value;
    return cuil;
};
function obtenerCuilFormulario(){

   const cuil=parseInt(document.getElementById('cuil2').value);
   return cuil;
}

// Manejar el envío del formulario de búsqueda
document.getElementById('buscar-empleado-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario
    
    const cuil = obtenerDatosBusqueda(); // Obtener el CUIL ingresado
    if (cuil) {
        buscarEmpleado(cuil); // Buscar empleado por cuil
    } else {
        alert('Por favor, ingrese un cuil para buscar.');
    }


});
document.getElementById('mostrar-empleado-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const cuil = obtenerCuilFormulario(); // Obtener el CUIL ingresado
    if (cuil) {
        eliminarEmpleado(cuil); // Buscar empleado por cuil

    } else {
        alert('Por favor, ingrese un cuil para buscar.');
    }


});


// codigo para obtener los paises y llenar el form donde se muestra el empleado.
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