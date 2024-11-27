// Función para mostrar los clientes en la tabla con los botones de eliminar y modificar
function mostrarClientesEnTabla(clientes) {
    const tbody = document.getElementById('clientes-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (clientes.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron clientes.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }

    // Recorrer los clientes y agregar las filas a la tabla
    clientes.forEach(cliente => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = cliente.idcliente;
        row.appendChild(idCell);

        const nombreCell = document.createElement('td');
        nombreCell.textContent = cliente.nombre_completo;
        row.appendChild(nombreCell);

        const direccionCell = document.createElement('td');
        direccionCell.textContent = cliente.direccion;
        row.appendChild(direccionCell);

        const emailCell = document.createElement('td');
        emailCell.textContent = cliente.mail;
        row.appendChild(emailCell);

        const telefonoCell = document.createElement('td');
        telefonoCell.textContent = cliente.telefono;
        row.appendChild(telefonoCell);

        const dniCell = document.createElement('td');
        dniCell.textContent = cliente.dni;
        row.appendChild(dniCell);

        const cuilCell = document.createElement('td');
        cuilCell.textContent = cliente.cuil;
        row.appendChild(cuilCell);

        const localidadCell = document.createElement('td');
        localidadCell.textContent = cliente.localidad_id.nombre;
        row.appendChild(localidadCell);

        // Columna para los botones de acción (Eliminar y Modificar)
        const accionesCell = document.createElement('td');

        // Botón de modificar con icono de Material Icons
        const verDetallesBtn = document.createElement('button');
        verDetallesBtn.className = 'btn btn-info btn-sm me-2';
        verDetallesBtn.innerHTML = '<span class="material-icons">visibility</span>'; // Icono de ver detalles
        verDetallesBtn.onclick = () => mostrarClienteEnFormulario(cliente);
        accionesCell.appendChild(verDetallesBtn);
        row.appendChild(accionesCell);
        tbody.appendChild(row);
    });
}

async function  mostrarClienteEnFormulario(cliente)
{

    const idpais=cliente.localidad_id.provincia.pais.idpais;
    const idProvincia=cliente.localidad_id.provincia.idprovincia;
    const idLocalidad=cliente.localidad_id.idlocalidad;
    await obtenerPaises();
    console.log(cliente);
    await obtenerProvincias(idpais);
    await obtenerLocalidades(idProvincia);

    document.getElementById('cuil2').value = String(cliente.cuil);
    document.getElementById('dni').value=String(cliente.dni);
    document.getElementById('nombre').value = cliente.nombre_completo;
    document.getElementById('email').value=cliente.mail;
    document.getElementById('telefono').value=String(cliente.telefono);
    document.getElementById('direccion').value=cliente.direccion;
    document.getElementById('fecha-nacimiento').value=cliente.fecha_nacimiento;
    document.getElementById('condicion-impositiva').value=cliente.condicion_impositiva;
    document.getElementById('borrado').value=cliente.borrado;
    document.getElementById('pais').value=idpais;
    document.getElementById('provincia').value=idProvincia;
    document.getElementById('localidad').value=idLocalidad;
    console.log(cliente);

    console.log("seleccionado");


}


// Cargar todos los clientes al iniciar la página
async function cargarClientes() {
    try {
        // Enviar la solicitud GET para obtener todos los clientes
        const response = await fetch('http://localhost:8080/api/adm/cliente/todos', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const clientes = await response.json();
            console.log('Clientes cargados:', clientes);
            mostrarClientesEnTabla(clientes); // Llamar a la función para mostrar todos los clientes
        } else {
            const errorData = await response.json();
            alert('Error: ' + (errorData.message || 'Hubo un error al cargar los clientes.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

window.onload = cargarClientes;



// Función para buscar el cliente por DNI
async function buscarCliente(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/cliente/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Cliente no encontrado');
        }

        const clienteBuscado = await response.json();

        // Convertir la respuesta a un arreglo para usar la misma función de mostrar
        mostrarClientesEnTabla([clienteBuscado]); // Llamamos la función para mostrar el cliente
        mostrarClienteEnFormulario(clienteBuscado);
    } catch (error) {
        console.error('Error al buscar cliente:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}

async function eliminarCliente(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/cliente/${cuil}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            alert('Cliente eliminado exitosamente!');  // Mensaje de éxito
            cargarClientes();
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta en caso de error
            alert('Error: ' + (errorData.message || 'Hubo un error al eliminar el cliente.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');

    }
}

async function modificarCliente(clienteActualizado, cuil) {
    try {
        // URL para la API de modificación del empleado, con el ID del empleado a modificar
        const response = await fetch(`http://localhost:8080/api/adm/cliente/${cuil}`, {
            method: 'PUT', // Usamos PUT para modificar un recurso existente
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(clienteActualizado)  // Los nuevos datos del empleado a modificar, incluidos el 'cuil'
        });

        if (response.ok) {
            alert('Empleado modificado exitosamente!');
            cargarClientes();// Mensaje de éxito
        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta con el mensaje de error
            alert('Error: ' + (errorData.message || 'Hubo un error al modificar el empleado.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

function obtenerDatosBusqueda() {
    const cuil = document.getElementById('cuil').value;
    return cuil;
}
function obtenerCuilFormulario(){

    const cuil=parseInt(document.getElementById('cuil2').value);
    return cuil;
}

// Manejar el envío del formulario de búsqueda
document.getElementById('buscar-cliente-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const cuil = obtenerDatosBusqueda(); // Obtener el DNI ingresado
    if (cuil) {
        buscarCliente(cuil); // Buscar cliente por DNI
    } else {
        alert('Por favor, ingrese un DNI para buscar.');
    }
});
document.getElementById('eliminar-boton').addEventListener('click', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const cuil = obtenerCuilFormulario(); // Obtener el CUIL ingresado
    if (cuil) {
        eliminarCliente(cuil); // Buscar empleado por cuil

    } else {
        alert('Por favor, ingrese un cuil para buscar.');
    }


});
document.getElementById('modificar-boton').addEventListener('click', function(event ) {
    // Aquí puedes agregar la lógica para modificar el empleado
    // Por ejemplo, puedes mostrar un mensaje de alerta o abrir un modal para confirmar la modificación
    event.preventDefault();
    const clienteActualizado = {
        CUIL: parseInt(document.getElementById('cuil2').value),
        dni:parseInt(document.getElementById('dni').value),
        nombre_completo: document.getElementById('nombre').value,
        telefono:parseInt(document.getElementById('telefono').value),
        mail: document.getElementById('email').value,
        direccion: document.getElementById('direccion').value,
        condicion_impositiva: document.getElementById('condicion-impositiva').value,
        fecha_nacimiento: document.getElementById('fecha-nacimiento').value,
        localidad_id: document.getElementById('localidad').value,

    };
    console.log(clienteActualizado);


    modificarCliente(clienteActualizado,clienteActualizado.CUIL);


});










//codigo de llenar paises y

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
