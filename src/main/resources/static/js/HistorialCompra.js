windowonlad=cargarCompras();

function cargarCompras() {
    document.addEventListener('DOMContentLoaded', async function () {
        // Obtener el valor de 'cuil' de la URL
        const urlParams = new URLSearchParams(window.location.search);
        const cuil = urlParams.get('cuil');
        document.getElementById('cuil').value=cuil;

        if (cuil) {
            console.log('CUIL recibido: ', cuil);

            //const id_ = await obtenerIdEmpleado(cuil);
            //console.log(id_empleado);
            await obtenerHistorialCompra(cuil);
            const general=await obtenerValoracionGeneral(cuil);
            await llenarValoracion(general);
        } else {
            console.log("No se recibió un CUIL válido en la URL");
        }
    });
}
async function llenarValoracion(val)
{
    document.getElementById('puntualidadgeneral').value=val.puntualiadPromedio;
    document.getElementById('cumplimientogeneral').value=val.cumplimientoPromedio;
    document.getElementById('calidadgeneral').value=val.calidadPromedio;

}

function mostrarHistorialCompraEnTabla(compras) {
    console.log(compras);
    const tbody = document.getElementById('compra-tbody');
    tbody.innerHTML = ''; // Limpiar la tabla antes de llenarla

    if (compras.length === 0) {
        const row = document.createElement('tr');
        const cell = document.createElement('td');
        cell.colSpan = 9; // Asegúrate de que el colspan cubra todas las columnas
        cell.textContent = 'No se encontraron historiales.';
        row.appendChild(cell);
        tbody.appendChild(row);
        return;
    }
    // Recorrer los compras y agregar las filas a la tabla
    compras.forEach(compra => {
        const row = document.createElement('tr');

        const idCell = document.createElement('td');
        idCell.textContent = compra.idcompra;
        row.appendChild(idCell);
        const productoCell = document.createElement('td');
        productoCell.textContent = compra.producto_servicio;
        row.appendChild(productoCell);
        const fechaCell = document.createElement('td');
        fechaCell.textContent = compra.fecha_compra;
        row.appendChild(fechaCell);
        const totalCell = document.createElement('td');
        totalCell.textContent = compra.total;
        row.appendChild(totalCell);

        // Columna para los botones de acción
        const accionesCell = document.createElement('td');
        // Botón de modificar con icono de Material Icons
        const valorarBtn = document.createElement('button');
        valorarBtn.className = 'btn btn-info btn-sm me-2';
        valorarBtn.innerHTML = '<span class="material-icons">🔍</span>';
        //valorarBtn.innerHTML = '<span class="material-icons">visibility</span>'; // Icono de ver detalles
        valorarBtn.onclick = () =>valorarCompra(compra);
        accionesCell.appendChild(valorarBtn);
        row.appendChild(accionesCell);
        tbody.appendChild(row);
    });
}

function valorarCompra(compra)
{
    const formCrear = document.getElementById('valorar-compra-form');
    formCrear.style.display = formCrear.style.display === 'none' ? 'block' : 'none';
    const boton = document.getElementById('activar-form-nueva compra');
    boton.style.display = 'none';

    document.getElementById('id_compra').value=compra.idcompra;

}

function obtenerDatosCompra(id_proveedor) {
    const compraNueva = {
        proveedor_id:id_proveedor,
        producto_servicio: document.getElementById('producto/servicio').value,
        total:parseInt(document.getElementById('importe').value),
        fecha_compra: document.getElementById('fecha-compra').value,


    };
    return compraNueva;
}
function obtenerDatosValoracion()
{
    const valoracion={
        compra_id:document.getElementById('id_compra').value,
        calidad:document.getElementById('calidad').value,
        puntualidad:document.getElementById('puntualidad').value,
        cumplimiento:document.getElementById('cumplimiento').value,
        notas:document.getElementById('notas').value
    }
    return valoracion;
}




async function obtenerIdProveedor(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/proveedor/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('empleado no encontrado');
        }

        const proveedorBuscado= await response.json();
        return proveedorBuscado.idproveedor;



    } catch (error) {
        console.error('Error al buscar empleado:', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}
async function obtenerHistorialCompra(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/compra/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('Historial no encontrado');
        }

        const historialCompra = await response.json();
        console.log(historialCompra);
        mostrarHistorialCompraEnTabla(historialCompra);




    } catch (error) {
        console.error('Error al buscar :', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}

async function obtenerValoracionGeneral(cuil) {
    try {
        const response = await fetch(`http://localhost:8080/api/adm/proveedor/valoraciongeneral/${cuil}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error('valoracion no encontrado');
        }

        const valoracionGeneral = await response.json();
        return valoracionGeneral;




    } catch (error) {
        console.error('Error al buscar :', error);
        alert('Hubo un problema al realizar la búsqueda. Intente nuevamente.');
    }
}


async function crearCompra(compra) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/compra', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(compra)
        });

        if (response.ok) {
            alert('compra guardada exitosamente!');

        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar la compra.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}
async function crearValoracion(valoracion) {
    try {
        const response = await fetch('http://localhost:8080/api/adm/valoracion', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(valoracion)
        });

        if (response.ok) {
            alert('valoracion guardada exitosamente!');

        } else {
            const errorData = await response.json();  // Obtener el cuerpo de la respuesta
            alert('Error: ' + (errorData.message || 'Hubo un error al guardar la valoracion.'));
        }
    } catch (error) {
        console.error('Error al hacer la solicitud:', error);
        alert('Hubo un problema al conectar con el servidor. Intente nuevamente.');
    }
}


document.getElementById('activar-form-nueva compra').addEventListener('click', function() {
    const formCrear = document.getElementById('form-crear-compra');
    formCrear.style.display = formCrear.style.display === 'none' ? 'block' : 'none';
});

document.getElementById('Nuevacompra').addEventListener('click', async function (event) {
    event.preventDefault();
    const urlParams = new URLSearchParams(window.location.search);
    const cuil = urlParams.get('cuil');
    console.log(cuil);
    const id_proveedor =await obtenerIdProveedor(cuil);
    const compra_nueva=  obtenerDatosCompra(id_proveedor);
    console.log(compra_nueva);
    await crearCompra(compra_nueva);
    reiniciarPagina();
    await cargarCompras();




});

document.getElementById('valorar-boton').addEventListener('click', async function (event) {
    event.preventDefault();
  const valoracion=obtenerDatosValoracion();
  console.log(valoracion);
  await crearValoracion(valoracion);
  reiniciarPagina();
  await cargarCompras();





});
function reiniciarPagina()
{
    const formCrear = document.getElementById('form-crear-compra');
    formCrear.style.display = 'none';
    const botonCrear = document.getElementById('activar-form-nueva compra');
    botonCrear.style.display = botonCrear.style.display === 'none' ? 'block' : 'none';
    const formValorar = document.getElementById('valorar-compra-form');
    formValorar.style.display = 'none';
}