async function cargarProveedores() {
    try {
        const buscarproveedores = await fetch('http://localhost:8080/api/adm/proveedor', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });


        const proveedores = await buscarproveedores.json();

        // Llenar el combo box con los proveedores
        const proveedorSelect = document.getElementById('proveedor');
        proveedorSelect.innerHTML = '<option value="">Seleccione un proveedor</option>'; // Limpiar opciones existentes

        // Añadir las opciones de países
        proveedores.forEach(proveedor => {
            const option = document.createElement('option');
            option.value = proveedor.idproveedor; // Asignar el ID del país (idpais)
            option.textContent = proveedor.nombre_completo; // Asignar el nombre del país
            proveedorSelect.appendChild(option);
        });
    } catch (error) {
        console.error('Error al obtener los países:', error);
    }
}



// Llamar a la función cuando la página cargue
window.onload = cargarProveedores;







