function mostrarProveedoresTabla(proveedores) {
    const tbody = document.getElementById('proveedores-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (proveedores.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron proveedores.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }

    // Recorrer los clientes y agregar las filas a la tabla
    proveedores.forEach(proveedor => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = proveedor.cuil;
        row.appendChild(idCell);

        const nombreCell = document.createElement('td');
        nombreCell.textContent = proveedor.nombre_completo;
        row.appendChild(nombreCell);

        const direccionCell = document.createElement('td');
        direccionCell.textContent = proveedor.direccion;
        row.appendChild(direccionCell);

        const emailCell = document.createElement('td');
        emailCell.textContent = proveedor.mail;
        row.appendChild(emailCell);

        const localidadCell = document.createElement('td');
        localidadCell.textContent = proveedor.localidad_id.nombre;
        row.appendChild(localidadCell);




        row.appendChild(accionesCell);

        tbody.appendChild(row);
    });
}


// Cargar todos los clientes al iniciar la página
async function cargarProveedores() {
    try {
        // Enviar la solicitud GET para obtener todos los clientes
        const response = await fetch('http://localhost:8080/api/adm/proveedor/', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            const proveedores = await response.json();

            mostrarProveedoresTabla(proveedores); // Llamar a la función para mostrar todos los Proveedores
        } else {
            const errorData = await response.json();
            alert('Error: ' + (errorData.message || 'Hubo un error al cargar los proveedores.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}

window.onload = cargarProveedores();


function obtenerDatosBusqueda() {
    const cuil = document.getElementById('cuilParaBuscar').value;
    return cuil;
};

// Función para buscar el cliente por DNI
async function buscarProveedor(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/proveedor/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Proveedor no encontrado');
        }

        const proveedorBuscado = await response.json();

        // Convertir la respuesta a un arreglo para usar la misma función de mostrar
        mostrarProveedoresEnTabla([proveedorBuscado]); // Llamamos la función para mostrar el cliente

    } catch (error) {
        console.error('Error al buscar proveedor:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}

// Manejar el envío del formulario de búsqueda
document.getElementById('buscar-proveedor-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Evitar el comportamiento por defecto del formulario

    const cuil = obtenerDatosBusqueda(); // Obtener el DNI ingresado
    if (cuil) {
        buscarProveedor(cuil); // Buscar cliente por DNI
    } else {
        alert('Por favor, ingrese un cuil para buscar.');
    }
});
