import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class INKAFARMA_FINAL {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    static String usuarioNombre, usuarioDni, usuarioCorreo, usuarioContraseña, usuarioTarjeta, usuarioClaveTarjeta;
    static String direccionCliente = "";
    static final String ADMIN_CORREO = "admin@inkafarma.com";
    static final String ADMIN_CONTRA = "Admin123$";
    static boolean esVendedor = false;

    static ArrayList<String> nombres = new ArrayList<>();
    static ArrayList<String> marcas = new ArrayList<>();
    static ArrayList<Double> precios = new ArrayList<>();
    static ArrayList<Integer> stocks = new ArrayList<>();
    static ArrayList<String> categorias = new ArrayList<>();

    static ArrayList<String> ofertasNombres = new ArrayList<>();
    static ArrayList<String> ofertasMarcas = new ArrayList<>();
    static ArrayList<Double> ofertasPrecios = new ArrayList<>();
    static ArrayList<Integer> ofertasStocks = new ArrayList<>();
    static ArrayList<String> ofertasCategorias = new ArrayList<>();

    static ArrayList<String> carrito = new ArrayList<>();
    static ArrayList<Integer> cantidades = new ArrayList<>();

    static ArrayList<String> vendedoresCorreos = new ArrayList<>();
    static ArrayList<String> vendedoresContras = new ArrayList<>();

    public static void registrarUsuario() {
        System.out.println("=== REGISTRO DE USUARIO ===");
        System.out.print("Nombre: ");
        usuarioNombre = sc.nextLine();
        do {
            System.out.print("DNI (8 dígitos): ");
            usuarioDni = sc.nextLine();
        } while (!usuarioDni.matches("\\d{8}"));
        do {
            System.out.print("Correo electrónico: ");
            usuarioCorreo = sc.nextLine();
        } while (!usuarioCorreo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));
        do {
            System.out.print("Contraseña (mín. 8 caracteres, 1 mayús., 1 minús., 1 número, 1 símbolo): ");
            usuarioContraseña = sc.nextLine();
        } while (!usuarioContraseña.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"));
        do {
            System.out.print("N° Tarjeta (16 dígitos): ");
            usuarioTarjeta = sc.nextLine();
        } while (!usuarioTarjeta.matches("\\d{16}"));
        do {
            System.out.print("Clave de tarjeta (4 dígitos): ");
            usuarioClaveTarjeta = sc.nextLine();
        } while (!usuarioClaveTarjeta.matches("\\d{4}"));
        System.out.println("Registro exitoso.\n");
    }

    public static boolean iniciarSesion() {
        System.out.println("=== INICIO DE SESIÓN ===");
        for (int i = 0; i < 3; i++) {
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Contraseña: ");
            String contra = sc.nextLine();

            if (correo.equals(ADMIN_CORREO) && contra.equals(ADMIN_CONTRA)) {
                System.out.println("Acceso como ADMIN");
                menuAdmin();
                return false;
            }

            for (int j = 0; j < vendedoresCorreos.size(); j++) {
                if (correo.equals(vendedoresCorreos.get(j)) && contra.equals(vendedoresContras.get(j))) {
                    System.out.println("Bienvenido, vendedor");
                    esVendedor = true;
                    menuPrincipal();
                    return false;
                }
            }

            if (correo.equals(usuarioCorreo) && contra.equals(usuarioContraseña)) {
                System.out.println("Bienvenido/a " + usuarioNombre);
                esVendedor = false;
                return true;
            }

            System.out.println("Intento fallido " + (i + 1) + "/3");
        }
        System.out.println("Acceso denegado");
        return false;
    }

    public static void agregarProducto(String nombre, String marca, double precio, int stock, String categoria) {
        nombres.add(nombre);
        marcas.add(marca);
        precios.add(precio);
        stocks.add(stock);
        categorias.add(categoria.toLowerCase());
    }

    public static void agregarOferta(String nombre, String marca, double precio, int stock, String categoria) {
        ofertasNombres.add(nombre);
        ofertasMarcas.add(marca);
        ofertasPrecios.add(precio);
        ofertasStocks.add(stock);
        ofertasCategorias.add(categoria.toLowerCase());
    }

    public static void menuAdmin() {
        while (true) {
            System.out.println("\n=== MENÚ ADMIN ===");
            System.out.println("1. Agregar producto");
            System.out.println("2. Agregar oferta");
            System.out.println("3. Registrar vendedor");
            System.out.println("4. Mostrar todos los productos");
            System.out.println("5. Mostrar todas las ofertas");
            System.out.println("6. Eliminar producto por índice");
            System.out.println("7. Salir del admin");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    ingresarProductoDesdeAdmin();
                    break;
                case "2":
                    ingresarOfertaDesdeAdmin();
                    break;
                case "3":
                    registrarVendedor();
                    break;
                case "4":
                    mostrarTodosLosProductos();
                    break;
                case "5":
                    mostrarOfertas();
                    break;
                case "6":
                    eliminarProductoPorIndice();
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    public static void ingresarProductoDesdeAdmin() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Marca: ");
        String marca = sc.nextLine();
        System.out.print("Precio: ");
        double precio = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int stock = Integer.parseInt(sc.nextLine());
        System.out.print("Categoría: ");
        String cat = sc.nextLine();
        agregarProducto(nombre, marca, precio, stock, cat);
        System.out.println("Producto agregado");
    }

    public static void ingresarOfertaDesdeAdmin() {
        System.out.print("Nombre de la oferta: ");
        String n = sc.nextLine();
        System.out.print("Marca: ");
        String m = sc.nextLine();
        System.out.print("Precio: ");
        double p = Double.parseDouble(sc.nextLine());
        System.out.print("Stock: ");
        int s = Integer.parseInt(sc.nextLine());
        System.out.print("Categoría: ");
        String cat = sc.nextLine();
        agregarOferta(n, m, p, s, cat);
        System.out.println("Oferta agregada");
    }

    public static void registrarVendedor() {
        System.out.println("=== REGISTRAR VENDEDOR ===");
        String correo, contra;
        do {
            System.out.print("Correo electrónico: ");
            correo = sc.nextLine();
        } while (!correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$"));

        do {
            System.out.print("Contraseña (mín. 8 caracteres, 1 mayús., 1 minús., 1 número, 1 símbolo): ");
            contra = sc.nextLine();
        } while (!contra.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$"));

        vendedoresCorreos.add(correo);
        vendedoresContras.add(contra);
        System.out.println("Vendedor registrado");
    }

    public static void mostrarTodosLosProductos() {
        System.out.println("\n=== TODOS LOS PRODUCTOS ===");
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + ". " + nombres.get(i) + " - " + marcas.get(i) + " - " + categorias.get(i) +
                    " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
        }
    }

    public static void mostrarOfertas() {
        for (int i = 0; i < ofertasNombres.size(); i++) {
            System.out.println(i + ". " +
                    ofertasNombres.get(i) + " - " +
                    ofertasMarcas.get(i) + " - " +
                    ofertasCategorias.get(i) + " - S/" +
                    ofertasPrecios.get(i) + " - Stock: " +
                    ofertasStocks.get(i));
        }
    }

    public static void eliminarProductoPorIndice() {
        mostrarTodosLosProductos();
        System.out.print("Índice del producto a eliminar: ");
        int i = Integer.parseInt(sc.nextLine());
        if (i >= 0 && i < nombres.size()) {
            nombres.remove(i);
            marcas.remove(i);
            precios.remove(i);
            stocks.remove(i);
            System.out.println("Producto eliminado");
        } else {
            System.out.println("Índice inválido");
        }
    }

    public static void menuPrincipal() {
        System.out.print("\nModo: ");
        if (esVendedor) {
            System.out.println("Vendedor");
        } else {
            System.out.println("Cliente");
        }

        System.out.println("\nOFERTA ESPECIAL:");
        if (!ofertasNombres.isEmpty()) {
            int indice = random.nextInt(ofertasNombres.size());
            String oferta = ofertasNombres.get(indice);
            double precio = ofertasPrecios.get(indice);
            System.out.println(oferta + " - S/" + precio);
            System.out.print("¿Deseas agregar esta oferta al carrito? (s/n): ");
            String respuesta = sc.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                if (ofertasStocks.get(indice) > 0) {
                    carrito.add(oferta);
                    cantidades.add(1);
                    ofertasStocks.set(indice, ofertasStocks.get(indice) - 1);
                    System.out.println("Oferta agregada al carrito.");
                } else {
                    System.out.println("Stock agotado para esta oferta.");
                }
            }
        }

        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver categorías");
            System.out.println("2. Ver carrito");
            System.out.println("3. Finalizar compra");
            System.out.println("4. Vaciar carrito");
            System.out.println("5. Eliminar producto del carrito");
            System.out.println("6. Salir");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "1":
                    categorias();
                    break;
                case "2":
                    verCarrito();
                    break;
                case "3":
                    finalizarCompra();
                    break;
                case "4":
                    vaciarCarrito();
                    break;
                case "5":
                    eliminarDelCarrito();
                    break;
                case "6":
                    return;
                default : System.out.println("Opción inválida");
            }
        }
    }

    public static void categorias() {
        while (true) {
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Mamá y Bebé");
            System.out.println("2. Nutrición");
            System.out.println("3. Regresar");
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1":
                    menuCategoria("mamá y bebé");
                    break;
                case "2":
                    menuCategoria("nutrición");
                    break;
                case "3":
                    return;
                default : System.out.println("Opción inválida");
            }
        }
    }

    public static void verCarrito() {
        System.out.println("\n=== TU CARRITO ===");
        double total = 0;
        for (int i = 0; i < carrito.size(); i++) {
            String nombre = carrito.get(i);
            int cant = cantidades.get(i);

            int index = nombres.indexOf(nombre);
            if (index == -1) index = ofertasNombres.indexOf(nombre);

            double precio = (index != -1 && nombres.contains(nombre)) ? precios.get(index) : ofertasPrecios.get(index);
            double precioTotal = precio * cant;
            total += precioTotal;

            System.out.printf("- %s x%d - S/ %.2f\n", nombre, cant, precioTotal);
        }
        System.out.printf("Total actual del carrito: S/ %.2f\n", total);
    }

    public static void vaciarCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("🛒 El carrito ya está vacío.");
            return;
        }
        carrito.clear();
        cantidades.clear();
        System.out.println("🗑️  Carrito vaciado.");
    }

    public static void eliminarDelCarrito() {
        if (carrito.isEmpty()) {
            System.out.println("🛒 El carrito está vacío.");
            return;
        }
        verCarrito();
        System.out.print("Índice del producto a eliminar: ");
        int idx = Integer.parseInt(sc.nextLine());

        if (idx < 0 || idx >= carrito.size()) {
            System.out.println("Índice inválido.");
            return;
        }

        System.out.print("Cantidad a quitar (máx " + cantidades.get(idx) + "): ");
        int qty = Integer.parseInt(sc.nextLine());

        if (qty <= 0 || qty > cantidades.get(idx)) {
            System.out.println("Cantidad inválida.");
            return;
        }

        String prod = carrito.get(idx);
        int invIndex = nombres.indexOf(prod);
        if (invIndex != -1) {
            stocks.set(invIndex, stocks.get(invIndex) + qty);
        } else {
            int offerIndex = ofertasNombres.indexOf(prod);
            if (offerIndex != -1) {
                ofertasStocks.set(offerIndex, ofertasStocks.get(offerIndex) + qty);
            }
        }

        int nuevaCant = cantidades.get(idx) - qty;
        if (nuevaCant == 0) {
            carrito.remove(idx);
            cantidades.remove(idx);
        } else {
            cantidades.set(idx, nuevaCant);
        }
        System.out.println("Producto actualizado/eliminado del carrito.");
    }

    public static void menuCategoria(String categoria) {
        while (true) {
            System.out.println("\n=== " + categoria.toUpperCase() + " ===");
            System.out.println("1. Mostrar productos");
            System.out.println("2. Filtrar por marca");
            System.out.println("3. Filtrar por precio");
            System.out.println("4. Regresar");
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1":
                    mostrarProductosDeCategoria(categoria);
                    break;
                case "2":
                    filtrarPorMarcaEnCategoria(categoria);
                    break;
                case "3":
                    filtrarPorPrecioEnCategoria(categoria);
                    break;
                case "4": return;
                default : System.out.println("Opción inválida");
            }
        }
    }

    public static void mostrarProductosDeCategoria(String categoria) {
        boolean hay = false;
        for (int i = 0; i < nombres.size(); i++) {
            if (categorias.get(i).equalsIgnoreCase(categoria)) {
                System.out.println(i + ". " + nombres.get(i) + " - "
                        + marcas.get(i) + " - S/" + precios.get(i)
                        + " - Stock: " + stocks.get(i));
                hay = true;
            }
        }
        if (!hay) {
            System.out.println("No hay productos en esta categoría.");
            return;
        }
        System.out.print("¿Deseas comprar alguno? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            agregarProductoAlCarritoPorIndice();
        }
    }

    public static void filtrarPorMarcaEnCategoria(String categoria) {
        System.out.print("Marca a buscar: ");
        String filtro = sc.nextLine().toLowerCase();
        ArrayList<Integer> idx = new ArrayList<>();

        for (int i = 0; i < nombres.size(); i++) {
            if (categorias.get(i).equalsIgnoreCase(categoria) &&
                    marcas.get(i).toLowerCase().contains(filtro)) {
                System.out.println(idx.size() + ". " + nombres.get(i) + " - "
                        + marcas.get(i) + " - S/" + precios.get(i)
                        + " - Stock: " + stocks.get(i));
                idx.add(i);
            }
        }
        agregarFiltradoAlCarrito(idx);
    }

    public static void filtrarPorPrecioEnCategoria(String categoria) {
        System.out.print("Precio mínimo: ");
        double min = Double.parseDouble(sc.nextLine());
        System.out.print("Precio máximo: ");
        double max = Double.parseDouble(sc.nextLine());

        System.out.println("Orden:");
        System.out.println("1. De menor a mayor");
        System.out.println("2. De mayor a menor");
        System.out.print("Opción: ");
        int orden = Integer.parseInt(sc.nextLine());

        ArrayList<Integer> idx = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            if (categorias.get(i).equalsIgnoreCase(categoria) &&
                    precios.get(i) >= min && precios.get(i) <= max) {
                idx.add(i);
            }
        }
        if (idx.isEmpty()) {
            System.out.println("No se encontraron productos.");
            return;
        }

        idx.sort((a, b) -> (orden == 2)
                ? Double.compare(precios.get(b), precios.get(a))
                : Double.compare(precios.get(a), precios.get(b)));

        for (int pos = 0; pos < idx.size(); pos++) {
            int i = idx.get(pos);
            System.out.println(pos + ". " + nombres.get(i) + " - " +
                    marcas.get(i) + " - S/" + precios.get(i) +
                    " - Stock: " + stocks.get(i));
        }
        agregarFiltradoAlCarrito(idx);
    }

    public static void agregarFiltradoAlCarrito(ArrayList<Integer> lista) {
        if (lista.isEmpty()) {
            System.out.println("No se encontraron productos.");
            return;
        }
        System.out.print("¿Deseas comprar alguno? (s/n): ");
        if (!sc.nextLine().equalsIgnoreCase("s"))
            return;

        System.out.print("Índice del listado filtrado: ");
        int pos = Integer.parseInt(sc.nextLine());
        if (pos < 0 || pos >= lista.size()) {
            System.out.println("Índice inválido.");
            return;
        }
        int real = lista.get(pos);
        System.out.print("Cantidad: ");
        int cant = Integer.parseInt(sc.nextLine());
        if (stocks.get(real) < cant) {
            System.out.println("Stock insuficiente.");
            return;
        }
        carrito.add(nombres.get(real));
        cantidades.add(cant);
        stocks.set(real, stocks.get(real) - cant);
        System.out.println("Producto agregado al carrito.");
    }

    public static void agregarProductoAlCarritoPorIndice() {
        System.out.print("Índice del producto: ");
        int indice = Integer.parseInt(sc.nextLine());
        System.out.print("Cantidad: ");
        int cant = Integer.parseInt(sc.nextLine());
        if (stocks.get(indice) >= cant) {
            carrito.add(nombres.get(indice));
            cantidades.add(cant);
            stocks.set(indice, stocks.get(indice) - cant);
            System.out.println("Producto agregado");
        } else {
            System.out.println("Stock insuficiente");
        }
    }

    public static void finalizarCompra() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío.");
            return;
        }

        double subtotal = 0;
        for (int i = 0; i < carrito.size(); i++) {
            String nombreProducto = carrito.get(i);
            int cantidad = cantidades.get(i);

            int index = nombres.indexOf(nombreProducto);
            if (index == -1) index = ofertasNombres.indexOf(nombreProducto);
            double precio = (index != -1 && nombres.contains(nombreProducto)) ? precios.get(index) : ofertasPrecios.get(index);

            subtotal += precio * cantidad;
        }

        double igv = subtotal * 0.18;
        double totalSinDelivery = subtotal + igv;

        final double COSTO_DELIVERY = 10.00;
        double total = totalSinDelivery;
        if (!esVendedor) {
            total += COSTO_DELIVERY;
        }

        System.out.printf("\nMonto total a pagar: S/ %.2f\n", total);
        System.out.print("¿Método de pago? (1. Tarjeta / 2. Efectivo): ");
        String metodo = sc.nextLine();

        String nombreComprador = "";
        String dniComprador = "";

        if (esVendedor) {
            System.out.print("Ingrese su nombre para la boleta: ");
            nombreComprador = sc.nextLine();
            System.out.print("Ingrese su DNI para la boleta: ");
            dniComprador = sc.nextLine();
        }
        else {
            nombreComprador = usuarioNombre;
            dniComprador = usuarioDni;
        }

        double montoPagado = 0;

        if (metodo.equals("2")) {
            System.out.print("Ingrese monto en efectivo: ");
            montoPagado = Double.parseDouble(sc.nextLine());
            if (montoPagado < total) {
                System.out.println("Monto insuficiente. Operación cancelada.");
                return;
            }
            if (!esVendedor) {
                System.out.print("Ingrese dirección para el envío: ");
                direccionCliente = sc.nextLine();
            }
        }
        else {
            montoPagado = total;
            if (!esVendedor) {
                System.out.print("Ingrese dirección para el envío: ");
                direccionCliente = sc.nextLine();
            }
        }
        boletaVenta(subtotal, igv, total, montoPagado, nombreComprador, dniComprador, !esVendedor ? COSTO_DELIVERY : 0);
        carrito.clear();
        cantidades.clear();
    }

    public static void boletaVenta(double subtotal, double igv, double total, double pagado, String nombreComprador, String dniComprador, double delivery) {

        double vuelto = pagado - total;

        System.out.println("============ INKAFARMA ============");
        System.out.println("====== BOLETA DE VENTA ======");
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        System.out.println("Fecha: " + LocalDateTime.now().format(formato));
        System.out.println("DNI: " + dniComprador);
        System.out.println("Nombre: " + nombreComprador);
        if (!esVendedor && direccionCliente != null && !direccionCliente.isEmpty()) {
            System.out.println("Dirección: " + direccionCliente);
        }
        System.out.println("\n--------------------------");
        System.out.println("Productos comprados:");
        for (int i = 0; i < carrito.size(); i++) {
            String nombre = carrito.get(i);
            int cant = cantidades.get(i);
            int index = nombres.indexOf(nombre);
            if (index == -1) index = ofertasNombres.indexOf(nombre);
            double precio = (index != -1 && nombres.contains(nombre)) ? precios.get(index) : ofertasPrecios.get(index);
            System.out.printf("- %s x%d - S/ %.2f\n", nombre, cant, precio * cant);
        }
        System.out.println("--------------------------");
        System.out.printf("\nSubtotal: S/ %.2f\n", subtotal);
        System.out.printf("IGV (18%%): S/ %.2f\n", igv);
        if (delivery > 0) {
            System.out.printf("Delivery: S/ %.2f\n", delivery);
        }
        System.out.printf("TOTAL: S/ %.2f\n", total);
        System.out.printf("Pagado: S/ %.2f\n", pagado);
        System.out.printf("Vuelto: S/ %.2f\n", vuelto);
        System.out.println("===================================");

        if (esVendedor) {
            exportarBoleta(subtotal, igv, total, pagado, vuelto, nombreComprador, dniComprador);
        }
    }

    public static void exportarBoleta(double subtotal, double igv, double total, double pagado, double vuelto, String nombre, String dni) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String fechaHora = LocalDateTime.now().format(formatter);
            String nombreArchivo = "boleta_" + fechaHora + ".txt";

            FileWriter fw = new FileWriter(nombreArchivo);
            PrintWriter pw = new PrintWriter(fw);

            pw.println("============ INKAFARMA ============");
            pw.println("======= BOLETA DE VENTA =======");
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            pw.println("Fecha: " + LocalDateTime.now().format(formato));
            pw.println("DNI: " + dni);
            pw.println("Nombre: " + nombre);
            pw.println("\n--------------------------");
            pw.println("Productos comprados:");
            for (int i = 0; i < carrito.size(); i++) {
                String nombreProd = carrito.get(i);
                int cant = cantidades.get(i);
                int index = nombres.indexOf(nombreProd);
                if (index == -1) index = ofertasNombres.indexOf(nombreProd);
                double precio = (index != -1 && nombres.contains(nombreProd)) ? precios.get(index) : ofertasPrecios.get(index);
                pw.printf("- %s x%d - S/ %.2f\n", nombreProd, cant, precio * cant);
            }
            pw.println("--------------------------\n");
            pw.printf("Subtotal: S/ %.2f\n", subtotal);
            pw.printf("IGV (18%%): S/ %.2f\n", igv);
            pw.printf("TOTAL: S/ %.2f\n", total);
            pw.printf("Pagado: S/ %.2f\n", pagado);
            pw.printf("Vuelto: S/ %.2f\n", vuelto);
            pw.println("===================================\n");

            pw.close();
            System.out.println("Boleta exportada como: " + nombreArchivo);
        }
        catch (IOException e) {
            System.out.println("Error al exportar la boleta.");
        }
    }

    public static void main(String[] args) {
        agregarProducto("Pañales Pequeñín", "Pequeñín", 25.90, 15,"mamá y bebé");
        agregarProducto("Toallitas Húmedas", "Pequeñín", 9.50, 20,"mamá y bebé");
        agregarProducto("Leche Enfamil", "BabyCare", 89.00, 10,"mamá y bebé");
        agregarProducto("Jabón Hipoalergénico", "BabyCare", 12.30, 30,"mamá y bebé");
        agregarProducto("Shampoo Bebé", "Mimi", 14.20, 25,"mamá y bebé");
        agregarProducto("Cereal Infantil", "Nestle", 10.50, 18,"mamá y bebé");
        agregarProducto("Biberón Avent", "Nestle", 34.90, 12,"mamá y bebé");
        agregarProducto("Pomada para Rozaduras", "Mimi", 22.80, 14,"mamá y bebé");

        agregarProducto("Batido Proteico Chocolate", "Sundown", 110.50, 10, "nutrición");
        agregarProducto("Multivitamínico Hombre", "Centrum", 56.90, 15, "nutrición");
        agregarProducto("Multivitamínico Mujer", "Centrum", 58.90, 12, "nutrición");
        agregarProducto("Omega 3 Pescado", "Sundown", 42.30, 20, "nutrición");
        agregarProducto("Vitamina C 1000mg", "Redoxon", 30.00, 18, "nutrición");
        agregarProducto("Colágeno Hidrolizado", "Redoxon", 125.00, 8, "nutrición");
        agregarProducto("Hierro + Ácido Fólico", "Ferrer", 22.70, 25, "nutrición");
        agregarProducto("Probióticos 10 cepas", "Ferrer", 65.40, 10, "nutrición");

        agregarOferta("Multivitaminas", "Centrum", 45.90, 4,"nutrición");
        agregarOferta("Proteína Whey", "Optimum", 125.00, 4,"nutrición");
        agregarOferta("Vitamina C 1000mg", "Sundown", 30.50, 4,"nutrición");
        agregarOferta("Omega 3", "Nature Made", 40.00, 4,"nutrición");

        vendedoresCorreos.add("vendedor@inkafarma.com");
        vendedoresContras.add("Vendedor123!");

        while (true) {
            System.out.println("\n=== INKAFARMA ===");
            System.out.println("1. Registrar");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine();
            switch (op) {
                case "1":
                    registrarUsuario();
                    break;
                case "2":
                    if (iniciarSesion()) {
                        menuPrincipal();
                    }
                    break;
                case "3":
                    System.out.println("Gracias por visitar INKAFARMA");
                    return;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
}
