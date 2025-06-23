import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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

    static ArrayList<String> ofertasNombres = new ArrayList<>();
    static ArrayList<String> ofertasMarcas = new ArrayList<>();
    static ArrayList<Double> ofertasPrecios = new ArrayList<>();
    static ArrayList<Integer> ofertasStocks = new ArrayList<>();

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
        System.out.println("✅ Registro exitoso.\n");
    }

    public static boolean iniciarSesion() {
        System.out.println("=== INICIO DE SESIÓN ===");
        for (int i = 0; i < 3; i++) {
            System.out.print("Correo: ");
            String correo = sc.nextLine();
            System.out.print("Contraseña: ");
            String contra = sc.nextLine();

            if (correo.equals(ADMIN_CORREO) && contra.equals(ADMIN_CONTRA)) {
                System.out.println("🔐 Acceso como ADMIN");
                menuAdmin();
                return false;
            }

            for (int j = 0; j < vendedoresCorreos.size(); j++) {
                if (correo.equals(vendedoresCorreos.get(j)) && contra.equals(vendedoresContras.get(j))) {
                    System.out.println("🔓 Bienvenido, vendedor");
                    esVendedor = true;
                    menuPrincipal();
                    return false;
                }
            }

            if (correo.equals(usuarioCorreo) && contra.equals(usuarioContraseña)) {
                System.out.println("✅ Bienvenido/a " + usuarioNombre);
                esVendedor = false;
                return true;
            }

            System.out.println("❌ Intento fallido " + (i + 1) + "/3");
        }
        System.out.println("🔒 Acceso denegado");
        return false;
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
                    mostrarProductosMamayBebe();
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
                    System.out.println("❌ Opción inválida");
            }
        }
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
        System.out.println("✅ Vendedor registrado");
    }

    public static void eliminarProductoPorIndice() {
        mostrarProductosMamayBebe();
        System.out.print("Índice del producto a eliminar: ");
        int i = Integer.parseInt(sc.nextLine());
        if (i >= 0 && i < nombres.size()) {
            nombres.remove(i);
            marcas.remove(i);
            precios.remove(i);
            stocks.remove(i);
            System.out.println("✅ Producto eliminado");
        } else {
            System.out.println("❌ Índice inválido");
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
        agregarProducto(nombre, marca, precio, stock);
        System.out.println("✅ Producto agregado");
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
        agregarOferta(n, m, p, s);
        System.out.println("✅ Oferta agregada");
    }

    public static void agregarProducto(String nombre, String marca, double precio, int stock) {
        nombres.add(nombre);
        marcas.add(marca);
        precios.add(precio);
        stocks.add(stock);
    }

    public static void agregarOferta(String nombre, String marca, double precio, int stock) {
        ofertasNombres.add(nombre);
        ofertasMarcas.add(marca);
        ofertasPrecios.add(precio);
        ofertasStocks.add(stock);
    }

    public static void menuPrincipal() {
        System.out.print("\n📋 Modo: ");
        if (esVendedor) {
            System.out.println("Vendedor");
        } else {
            System.out.println("Cliente");
        }

        System.out.println("\n🎯 OFERTA ESPECIAL:");
        if (!ofertasNombres.isEmpty()) {
            int indice = random.nextInt(ofertasNombres.size());
            String oferta = ofertasNombres.get(indice);
            double precio = ofertasPrecios.get(indice);
            System.out.println("👉 " + oferta + " - S/" + precio);
            System.out.print("¿Deseas agregar esta oferta al carrito? (s/n): ");
            String respuesta = sc.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                if (ofertasStocks.get(indice) > 0) {
                    carrito.add(oferta);
                    cantidades.add(1);
                    ofertasStocks.set(indice, ofertasStocks.get(indice) - 1);
                    System.out.println("🛒 Oferta agregada al carrito.");
                } else {
                    System.out.println("❌ Stock agotado para esta oferta.");
                }
            }
        }

        while (true) {
            System.out.println("\n=== MENÚ PRINCIPAL ===");
            System.out.println("1. Ver categorías");
            System.out.println("2. Ver carrito");
            System.out.println("3. Finalizar compra");
            System.out.println("4. Salir");
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
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }

    public static void categorias() {
        while (true) {
            System.out.println("\n=== CATEGORÍAS ===");
            System.out.println("1. Mamá y Bebé");
            System.out.println("2. Regresar");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    mamaybebe();
                    break;
                case "2":
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }

    public static void verCarrito() {
        System.out.println("\n=== TU CARRITO ===");
        for (int i = 0; i < carrito.size(); i++) {
            System.out.println("- " + carrito.get(i) + " x" + cantidades.get(i));
        }
    }

    public static void mamaybebe() {
        while (true) {
            System.out.println("\n=== MAMÁ Y BEBÉ ===");
            System.out.println("1. Mostrar productos");
            System.out.println("2. Filtrar por marca");
            System.out.println("3. Filtrar por precio");
            System.out.println("4. Regresar");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    mostrarProductosMamayBebe();
                    break;
                case "2":
                    filtrarPorMarca();
                    break;
                case "3":
                    filtrarPorPrecio();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }


    public static void mostrarProductosMamayBebe() {
        for (int i = 0; i < nombres.size(); i++) {
            System.out.println(i + ". " + nombres.get(i) + " - " + marcas.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
        }

        System.out.print("¿Deseas comprar alguno de estos productos? (s/n): ");
        String respuesta = sc.nextLine().toLowerCase();
        if (respuesta.equals("s")) {
            agregarProductoAlCarritoPorIndice();
        }
    }


    public static void mostrarOfertas() {
        for (int i = 0; i < ofertasNombres.size(); i++) {
            System.out.println(i + ". " + ofertasNombres.get(i) + " - " + ofertasMarcas.get(i) + " - S/" + ofertasPrecios.get(i) + " - Stock: " + ofertasStocks.get(i));
        }
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
            System.out.println("✅ Producto agregado");
        } else {
            System.out.println("❌ Stock insuficiente");
        }
    }

    public static void filtrarPorMarca() {
        System.out.print("Marca a buscar: ");
        String filtro = sc.nextLine().toLowerCase();

        ArrayList<Integer> indicesFiltrados = new ArrayList<>();
        for (int i = 0; i < nombres.size(); i++) {
            if (marcas.get(i).toLowerCase().contains(filtro)) {
                System.out.println(indicesFiltrados.size() + ". " + nombres.get(i) + " - " + marcas.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
                indicesFiltrados.add(i);
            }
        }

        if (indicesFiltrados.isEmpty()) {
            System.out.println("❌ No se encontraron productos.");
            return;
        }

        System.out.print("¿Deseas agregar alguno al carrito? (s/n): ");
        String opcion = sc.nextLine().toLowerCase();

        if (opcion.equals("s")) {
            System.out.print("Índice del producto filtrado: ");
            int idxTemp = Integer.parseInt(sc.nextLine());

            if (idxTemp >= 0 && idxTemp < indicesFiltrados.size()) {
                int idxReal = indicesFiltrados.get(idxTemp);
                System.out.print("Cantidad a agregar: ");
                int cantidad = Integer.parseInt(sc.nextLine());
                if (stocks.get(idxReal) >= cantidad) {
                    carrito.add(nombres.get(idxReal));
                    cantidades.add(cantidad);
                    stocks.set(idxReal, stocks.get(idxReal) - cantidad);
                    System.out.println("✅ Producto agregado al carrito.");
                } else {
                    System.out.println("❌ Stock insuficiente.");
                }
            } else {
                System.out.println("❌ Índice inválido.");
            }
        }
    }


    public static void filtrarPorPrecio() {
        System.out.print("Precio mínimo: ");
        double min = Double.parseDouble(sc.nextLine());
        System.out.print("Precio máximo: ");
        double max = Double.parseDouble(sc.nextLine());

        ArrayList<Integer> indicesFiltrados = new ArrayList<>();
        for (int i = 0; i < precios.size(); i++) {
            if (precios.get(i) >= min && precios.get(i) <= max) {
                System.out.println(indicesFiltrados.size() + ". " + nombres.get(i) + " - " + marcas.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
                indicesFiltrados.add(i);
            }
        }

        if (indicesFiltrados.isEmpty()) {
            System.out.println("❌ No se encontraron productos.");
            return;
        }

        System.out.print("¿Deseas agregar alguno al carrito? (s/n): ");
        String opcion = sc.nextLine().toLowerCase();

        if (opcion.equals("s")) {
            System.out.print("Índice del producto filtrado: ");
            int idxTemp = Integer.parseInt(sc.nextLine());

            if (idxTemp >= 0 && idxTemp < indicesFiltrados.size()) {
                int idxReal = indicesFiltrados.get(idxTemp);
                System.out.print("Cantidad a agregar: ");
                int cantidad = Integer.parseInt(sc.nextLine());
                if (stocks.get(idxReal) >= cantidad) {
                    carrito.add(nombres.get(idxReal));
                    cantidades.add(cantidad);
                    stocks.set(idxReal, stocks.get(idxReal) - cantidad);
                    System.out.println("✅ Producto agregado al carrito.");
                } else {
                    System.out.println("❌ Stock insuficiente.");
                }
            } else {
                System.out.println("❌ Índice inválido.");
            }
        }
    }


    public static void finalizarCompra() {
        if (carrito.isEmpty()) {
            System.out.println("❌ El carrito está vacío.");
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
        double total = subtotal + igv;

        System.out.printf("\n💳 Monto total a pagar: S/ %.2f\n", total);
        System.out.print("¿Método de pago? (1. Tarjeta / 2. Efectivo): ");
        String metodo = sc.nextLine();

        String nombreComprador = "";
        String dniComprador = "";

        if (esVendedor) {
            System.out.print("Ingrese su nombre para la boleta: ");
            nombreComprador = sc.nextLine();
            System.out.print("Ingrese su DNI para la boleta: ");
            dniComprador = sc.nextLine();
        } else {
            nombreComprador = usuarioNombre;
            dniComprador = usuarioDni;
        }

        if (metodo.equals("2")) {
            System.out.print("Ingrese monto en efectivo: ");
            double monto = Double.parseDouble(sc.nextLine());
            if (monto < total) {
                System.out.println("❌ Monto insuficiente. Operación cancelada.");
                return;
            }

            if (!esVendedor) {
                System.out.print("Ingrese dirección para el envío: ");
                direccionCliente = sc.nextLine();
            }

            boletaVenta(total, monto, nombreComprador, dniComprador);
        } else {
            if (!esVendedor) {
                System.out.print("Ingrese dirección para el envío: ");
                direccionCliente = sc.nextLine();
            }

            boletaVenta(total, total, nombreComprador, dniComprador);
        }

        carrito.clear();
        cantidades.clear();
    }

    public static void boletaVenta(double total, double pagado, String nombreComprador, String dniComprador) {
        double subtotal = total / 1.18;
        double igv = total - subtotal;
        double vuelto = pagado - total;

        System.out.println("\n======= BOLETA DE VENTA =======");
        System.out.println("DNI: " + dniComprador);
        System.out.println("Nombre: " + nombreComprador);
        if (!esVendedor && direccionCliente != null && !direccionCliente.isEmpty()) {
            System.out.println("Dirección: " + direccionCliente);
        }

        System.out.println("\nProductos comprados:");
        for (int i = 0; i < carrito.size(); i++) {
            String nombre = carrito.get(i);
            int cant = cantidades.get(i);
            int index = nombres.indexOf(nombre);
            if (index == -1) index = ofertasNombres.indexOf(nombre);
            double precio = (index != -1 && nombres.contains(nombre)) ? precios.get(index) : ofertasPrecios.get(index);
            System.out.printf("- %s x%d - S/ %.2f\n", nombre, cant, precio * cant);
        }

        System.out.printf("\nSubtotal: S/ %.2f\n", subtotal);
        System.out.printf("IGV (18%%): S/ %.2f\n", igv);
        System.out.printf("TOTAL: S/ %.2f\n", total);
        System.out.printf("Pagado: S/ %.2f\n", pagado);
        System.out.printf("Vuelto: S/ %.2f\n", vuelto);
        System.out.println("==============================");

        if (esVendedor) {
            exportarBoleta(subtotal, igv, total, pagado, vuelto, nombreComprador, dniComprador);
        }
    }

    public static void exportarBoleta(double subtotal, double igv, double total, double pagado, double vuelto, String nombre, String dni) {
        try {
            FileWriter fw = new FileWriter("boleta_ventas.txt", true); // true = modo append
            PrintWriter pw = new PrintWriter(fw);

            pw.println("======= BOLETA DE VENTA =======");
            pw.println("DNI: " + dni);
            pw.println("Nombre: " + nombre);
            pw.println("\nProductos comprados:");
            for (int i = 0; i < carrito.size(); i++) {
                String nombreProd = carrito.get(i);
                int cant = cantidades.get(i);
                int index = nombres.indexOf(nombreProd);
                if (index == -1) index = ofertasNombres.indexOf(nombreProd);
                double precio = (index != -1 && nombres.contains(nombreProd)) ? precios.get(index) : ofertasPrecios.get(index);
                pw.printf("- %s x%d - S/ %.2f\n", nombreProd, cant, precio * cant);
            }

            pw.printf("\nSubtotal: S/ %.2f\n", subtotal);
            pw.printf("IGV (18%%): S/ %.2f\n", igv);
            pw.printf("TOTAL: S/ %.2f\n", total);
            pw.printf("Pagado: S/ %.2f\n", pagado);
            pw.printf("Vuelto: S/ %.2f\n", vuelto);
            pw.println("================================\n");

            pw.close();
            System.out.println("📄 Boleta exportada a 'boleta_ventas.txt'");
        }
        catch (IOException e) {
            System.out.println("❌ Error al exportar la boleta.");
        }
    }

    public static void main(String[] args) {
        agregarProducto("Pañales Pequeñín", "Pequeñín", 25.90, 15);
        agregarProducto("Toallitas Húmedas", "Pequeñín", 9.50, 20);
        agregarProducto("Leche Enfamil", "BabyCare", 89.00, 10);
        agregarProducto("Jabón Hipoalergénico", "BabyCare", 12.30, 30);
        agregarProducto("Shampoo Bebé", "Johnsons", 14.20, 25);
        agregarProducto("Cereal Infantil", "Nestlé", 10.50, 18);
        agregarProducto("Biberón Avent", "Nestlé", 34.90, 12);
        agregarProducto("Pomada para Rozaduras", "Johnsons", 22.80, 14);

        agregarOferta("Multivitaminas", "Centrum", 45.90, 8);
        agregarOferta("Proteína Whey", "Optimum", 125.00, 5);
        agregarOferta("Vitamina C 1000mg", "Sundown", 30.50, 10);
        agregarOferta("Omega 3", "Nature Made", 40.00, 6);

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
                    System.out.println("👋 Gracias por visitar INKAFARMA");
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }
}
