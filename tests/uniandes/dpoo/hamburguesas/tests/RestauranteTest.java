package uniandes.dpoo.hamburguesas.tests;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import uniandes.dpoo.hamburguesas.excepciones.HamburguesaException;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;

public class RestauranteTest {
	
	//preparacion variables usadas
	private final String nombreCliente = "alejandro";
	private final String direccionCliente = "calle 70 #5-22";
	private final String nombreCliente2 = "jaime";
	private final String direccionCliente2 = "calle 80 #13-10";
	private Ingrediente lechuga = new Ingrediente("lechuga", 1000);
	private Ingrediente tomate = new Ingrediente("tomate", 1000);
	private Ingrediente cebolla = new Ingrediente("cebolla", 1000);
	private Pedido pedido = new Pedido(nombreCliente, direccionCliente);
	private ProductoAjustado productoAjustado;
	
	//preparacion creacion combo
	private ProductoMenu todoterreno = new ProductoMenu("todoterreno", 25000);
	private ProductoMenu corral = new ProductoMenu("corral", 14000);
	private ProductoMenu papasM = new ProductoMenu("papas medianas", 5500);
	private ProductoMenu papasG = new ProductoMenu("papas grandes", 6900);
	private ProductoMenu gaseosa = new ProductoMenu("gaseosa", 5000);
	private final ArrayList<ProductoMenu> items = new ArrayList<>();
	private final ArrayList<ProductoMenu> items2 = new ArrayList<>();
	private Combo combo1 = new Combo("combo corral", 0.1, items);
	private Combo combo2 = new Combo("combo todoterreno", 0.07, items2);
	
	//preparacion archivos
	private File archivoIngredientes;
	private File archivoMenu;
	private File archivoCombos;
	
	Restaurante restaurante;
	
	@BeforeEach
	void setup() throws Exception{
		
		//creo instancia de restaurante
		restaurante = new Restaurante();
		
		//add pedido a lista de pedidos
		restaurante.getPedidos().add(pedido);
		
		archivoIngredientes = File.createTempFile("testIngrediente", ".txt");
		try (FileWriter writer = new FileWriter(archivoIngredientes)){
			writer.write("lechuga;1000\n");
			writer.write("tomate;1000\n");
			writer.write("cebolla;1000\n");
		}			
		archivoMenu = File.createTempFile("testMenu", ".txt");
		try(FileWriter writer = new FileWriter(archivoMenu)){
			writer.write("corral;14000\n");
			writer.write("todoterreno;25000\n");
			writer.write("papas medianas;5500\n");
			writer.write("papas grandes;6900\n");
			writer.write("gaseosa;5000\n");
		}
		archivoCombos = File.createTempFile("testCombos", ".txt");
		try(FileWriter writer = new FileWriter(archivoCombos)){
			writer.write("combo corral;10%;corral;papas medianas;gaseosa\n");
			writer.write("combo todoterreno;7%;todoterreno;papas grandes;gaseosa\n");
			
		}	
		
		File facturasDir = new File(restaurante.getCarpetaFacturas());

    }
 


	
	@Test
	public void testGetPedido() {
		assertTrue(restaurante.getPedidos().contains(pedido));
	}

	@Test
	public void testGetIngredientes() {
		restaurante.getIngredientes().add(lechuga);
		restaurante.getIngredientes().add(tomate);
		restaurante.getIngredientes().add(cebolla);
		
		assertTrue(restaurante.getIngredientes().contains(lechuga));
		assertTrue(restaurante.getIngredientes().contains(tomate));
		assertTrue(restaurante.getIngredientes().contains(cebolla));
	}
	
	@Test
	public void testGetMenuBase() {
		restaurante.getMenuBase().add(todoterreno);
		restaurante.getMenuBase().add(corral);
		
		assertTrue(restaurante.getMenuBase().contains(todoterreno));
		assertTrue(restaurante.getMenuBase().contains(corral));
	}
	
	@Test
	public void testGetMenuCombos() {
		items.add(corral);
		items.add(papasM);
		items.add(gaseosa);
		restaurante.getMenuCombos().add(combo1);
		items2.add(todoterreno);
		items2.add(papasG);
		items2.add(gaseosa);
		restaurante.getMenuCombos().add(combo2);
		
		assertTrue(restaurante.getMenuCombos().contains(combo1));
		assertTrue(restaurante.getMenuCombos().contains(combo2));
	}
	
	@Test
	public void testGetPedidoEnCursoNoHayPedido() {
		assertNull(restaurante.getPedidoEnCurso());
	}
	
	@Test
	public void testIniciarPedidoNull() {
		assertDoesNotThrow(() -> restaurante.iniciarPedido(nombreCliente, direccionCliente));
		Pedido enCurso = restaurante.getPedidoEnCurso();
		
		assertNotNull(enCurso);
		assertEquals(nombreCliente, enCurso.getNombreCliente());
		assertEquals(direccionCliente, enCurso.getDireccionCliente());
	}
	
	@Test
	public void testIniciarPedidoYaHayPedido() throws YaHayUnPedidoEnCursoException {
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
		
		Exception exception = assertThrows(Exception.class, () -> restaurante.iniciarPedido(nombreCliente2, direccionCliente2));
		
		YaHayUnPedidoEnCursoException pedidoExcep = new YaHayUnPedidoEnCursoException(nombreCliente, nombreCliente2);
		
		String msjEsperado = pedidoExcep.getMessage();
		assertEquals(msjEsperado, exception.getMessage());
	}
	
	@Test
	public void testCerrarYGuardarNoHayPedido() throws NoHayPedidoEnCursoException, IOException {
		
		assertNull(restaurante.getPedidoEnCurso());
		Exception exception = assertThrows(Exception.class, () -> restaurante.cerrarYGuardarPedido());
		
		NoHayPedidoEnCursoException pedidoExcep = new NoHayPedidoEnCursoException();
		String msjEsperado = pedidoExcep.getMessage();
		
		assertEquals(msjEsperado, exception.getMessage());
		
		
	}
	
	@Test
	public void testCerrarYGuardarSiHayPedido() throws NoHayPedidoEnCursoException, IOException, YaHayUnPedidoEnCursoException{
		restaurante.iniciarPedido(nombreCliente, direccionCliente);
		Pedido enCurso = restaurante.getPedidoEnCurso();
        String expectedFilePath = restaurante.getCarpetaFacturas() + restaurante.getPrefijoFacturas() + enCurso.getIdPedido() + ".txt";

        restaurante.cerrarYGuardarPedido();
        
        File facturaFile = new File(expectedFilePath);
        assertTrue(facturaFile.exists());

        assertNull(restaurante.getPedidoEnCurso());
        
        facturaFile.delete();
		
	}
	 
	@Test
	public void testCargarInformacionRestaurantes() throws HamburguesaException, NumberFormatException, IOException {
		assertDoesNotThrow(() -> restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos));
		
		assertEquals(3, restaurante.getIngredientes().size());
		assertEquals(5, restaurante.getMenuBase().size());
		assertEquals(2, restaurante.getMenuCombos().size());
		
	}
	 
	@Test
	public void testCargarIngredientesRepetidos() throws IngredienteRepetidoException, IOException {
		try (FileWriter writer = new FileWriter(archivoIngredientes, true)){
			writer.write("lechuga;1000\n");
			}
		
		Exception exception = assertThrows(Exception.class, () -> restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos));
		IngredienteRepetidoException repetidoExcep = new IngredienteRepetidoException("lechuga");
		String msjEsperado = repetidoExcep.getMessage();
		
		assertEquals(msjEsperado, exception.getMessage());
	}

	@Test
	public void testCargarMenuBaseRepetidos() throws IOException, ProductoRepetidoException {
		try(FileWriter writer = new FileWriter(archivoMenu, true)){
			 writer.write("corral;14000\n");
		}
		
		Exception exception = assertThrows(Exception.class, () -> restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos));
		ProductoRepetidoException repetidoExcep = new ProductoRepetidoException("corral");
		String msjEsperado = repetidoExcep.getMessage();
		
		assertEquals(msjEsperado, exception.getMessage());
	}
	 
	@Test
	public void testCargarMenuCombosRepetidos() throws IOException, ProductoRepetidoException {
		try(FileWriter writer = new FileWriter(archivoCombos, true)) {
			
			writer.write("combo corral;10%;corral;papas medianas;gaseosa\n");

		}
		
		Exception exception = assertThrows(Exception.class, () -> restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos));
		ProductoRepetidoException repetidoExcep = new ProductoRepetidoException("combo corral");
		String msjEsperado = repetidoExcep.getMessage();
		
		assertEquals(msjEsperado, exception.getMessage());
	}
	
	@Test
	public void testProductoFaltante() throws IOException, ProductoFaltanteException {
		try(FileWriter writer = new FileWriter(archivoCombos, true)){
			writer.write("combo corral queso;10%;corral queso;papas medianas;gaseosa");
		}
		
		Exception exception = assertThrows(Exception.class, () -> restaurante.cargarInformacionRestaurante(archivoIngredientes, archivoMenu, archivoCombos));
		ProductoFaltanteException faltanteExcep = new ProductoFaltanteException("corral queso");
		String msjEsperado = faltanteExcep.getMessage();
		
		assertEquals(msjEsperado, exception.getMessage());
	}
	
	
	
	
	
	
	
	
	
	
}