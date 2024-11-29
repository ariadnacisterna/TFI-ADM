// Agregar un evento al formulario para capturar el envío
document.getElementById('ingresar-datos-form').addEventListener('submit', async function (event) {
    event.preventDefault(); // Evita el comportamiento predeterminado del formulario

    // Obtener los valores de los campos del formulario
    const cuilCliente = document.getElementById('cuil-cliente').value.trim();
    const cuilEmpleado = document.getElementById('cuil-empleado').value.trim();

    // Verificar existencia de cliente y empleado en el backend
    const clienteExiste = await verificarCliente(cuilCliente);
    const empleadoExiste = await verificarEmpleado(cuilEmpleado);

    // Si ambos existen, enviar la venta
    if (clienteExiste && empleadoExiste) {
        console.log('El cliente y el empleado existen.');
        alert('Cliente y empleado encontrados exitosamente.');

        // Llamar a la función para enviar la venta
        const ventaCreada = await crearVenta(cuilCliente, cuilEmpleado);

        if (ventaCreada) {
            alert('Venta creada exitosamente.');
        } else {
            alert('Error al crear la venta.');
        }
    } else {
        // Mostrar mensajes de error en caso de que alguno no exista
        if (!clienteExiste) {
            console.error('El cliente no existe.');
            alert('El cliente no existe en la base de datos.');
        }
        if (!empleadoExiste) {
            console.error('El empleado no existe.');
            alert('El empleado no existe en la base de datos.');
        }
    }
});

// Función para verificar la existencia del cliente
async function verificarCliente(cuil) {
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

        console.log('Cliente encontrado');
        return true; // Cliente encontrado
    } catch (error) {
        console.error('Error al verificar cliente:', error);
        return false; // Cliente no encontrado
    }
}

// Función para verificar la existencia del empleado
async function verificarEmpleado(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/empleados/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Empleado no encontrado');
        }

        console.log('Empleado encontrado');
        return true; // Empleado encontrado
    } catch (error) {
        console.error('Error al verificar empleado:', error);
        return false; // Empleado no encontrado
    }
}

async function crearVenta(clienteCUIL, empleadoCUIL) {
    // Crear el objeto VentaRequest
    const ventaRequest = {
        cliente_CUIL: clienteCUIL,
        empleado_CUIL: empleadoCUIL
    };

    try {
        const response = await fetch('http://localhost:8080/api/adm/ventas/crear', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ventaRequest) // Convertir el objeto a JSON
        });

        if (!response.ok) {
            const errorDetail = await response.text(); // Obtener el detalle del error
            throw new Error(`Error al crear la venta: ${response.status} ${errorDetail}`);
        }

        const data = await response.json(); // Parsear la respuesta JSON
        console.log('Venta creada exitosamente:', data);
        return true; // Venta creada con éxito
    } catch (error) {
        console.error('Error al crear la venta:', error);
        return false; // Error al crear la venta
    }
}
