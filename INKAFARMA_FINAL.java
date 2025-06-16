import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class INKAFARMA_FINAL {
    static Scanner sc = new Scanner(System.in);
    static Random random = new Random();

    static String usuarioNombre, usuarioDni, usuarioCorreo, usuarioContraseña, usuarioTarjeta, usuarioClaveTarjeta;
    static final String ADMIN_CORREO = "admin@inkafarma.com";
    static final String ADMIN_CONTRA = "Admin123$";

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
            if (correo.equals(usuarioCorreo) && contra.equals(usuarioContraseña)) {
                System.out.println("✅ Bienvenido/a " + usuarioNombre);
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
            System.out.println("3. Salir del admin");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
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
                    break;
                case "2":
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
                    break;
                case "3":
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
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
            System.out.println("3. Salir");
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
            System.out.println("2. Agregar al carrito por índice");
            System.out.println("3. Filtrar por marca");
            System.out.println("4. Filtrar por precio");
            System.out.println("5. Regresar");
            System.out.print("Opción: ");
            String opcion = sc.nextLine();
            switch (opcion) {
                case "1":
                    for (int i = 0; i < nombres.size(); i++) {
                        System.out.println(i + ". " + nombres.get(i) + " - " + marcas.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
                    }
                    break;
                case "2":
                    System.out.print("Índice del producto: ");
                    int idx = Integer.parseInt(sc.nextLine());
                    System.out.print("Cantidad: ");
                    int cant = Integer.parseInt(sc.nextLine());
                    if (stocks.get(idx) >= cant) {
                        carrito.add(nombres.get(idx));
                        cantidades.add(cant);
                        stocks.set(idx, stocks.get(idx) - cant);
                        System.out.println("✅ Producto agregado");
                    } else {
                        System.out.println("❌ Stock insuficiente");
                    }
                    break;
                case "3":
                    filtrarPorMarca();
                    break;
                case "4":
                    filtrarPorPrecio();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("❌ Opción inválida");
            }
        }
    }

    public static void filtrarPorMarca() {
        System.out.print("Marca a buscar: ");
        String filtro = sc.nextLine().toLowerCase();
        for (int i = 0; i < nombres.size(); i++) {
            if (marcas.get(i).toLowerCase().contains(filtro)) {
                System.out.println(i + ". " + nombres.get(i) + " - " + marcas.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
            }
        }
    }

    public static void filtrarPorPrecio() {
        System.out.print("Precio mínimo: ");
        double min = Double.parseDouble(sc.nextLine());
        System.out.print("Precio máximo: ");
        double max = Double.parseDouble(sc.nextLine());
        for (int i = 0; i < nombres.size(); i++) {
            if (precios.get(i) >= min && precios.get(i) <= max) {
                System.out.println(i + ". " + nombres.get(i) + " - S/" + precios.get(i) + " - Stock: " + stocks.get(i));
            }
        }
    }

    public static void main(String[] args) {
        agregarProducto("Pañales Pequeñín", "Pequeñín", 25.90, 15);
        agregarProducto("Toallitas Húmedas", "Pequeñín", 9.50, 20);
        agregarProducto("Leche Enfamil", "MeadJohnson", 89.00, 10);
        agregarProducto("Jabón Hipoalergénico", "BabyCare", 12.30, 30);
        agregarProducto("Shampoo Bebé", "Johnson's", 14.20, 25);
        agregarProducto("Cereal Infantil", "Nestlé", 10.50, 18);
        agregarProducto("Biberón Avent", "Philips", 34.90, 12);
        agregarProducto("Pomada para Rozaduras", "Desitin", 22.80, 14);
        agregarOferta("Multivitaminas", "Centrum", 45.90, 8);
        agregarOferta("Proteína Whey", "Optimum", 125.00, 5);
        agregarOferta("Vitamina C 1000mg", "Sundown", 30.50, 10);
        agregarOferta("Omega 3", "Nature Made", 40.00, 6);
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