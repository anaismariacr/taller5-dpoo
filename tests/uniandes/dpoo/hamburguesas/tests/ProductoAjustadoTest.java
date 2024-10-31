package uniandes.dpoo.hamburguesas.tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ProductoAjustadoTest {

	private Ingrediente lechuga = new Ingrediente("lechuga", 1000);
	private Ingrediente tomate = new Ingrediente("tomate", 1000);
	private Ingrediente cebolla = new Ingrediente("cebolla", 1000);
	
	private final String nombreProducto = "corral queso";
	private final int precioProductoMenu = 16000;
	private final ArrayList<Ingrediente> agregados = new ArrayList<>();
	private final ArrayList<Ingrediente> eliminados = new ArrayList<>();
	
	private ProductoMenu productoBase;
	private ProductoAjustado producto;
	
	@BeforeEach
	void setup() throws Exception{
		productoBase = new ProductoMenu(nombreProducto, precioProductoMenu);
		producto = new ProductoAjustado(productoBase, agregados, eliminados);
	}
	
	@Test
	public void testNombre() {
		assertEquals(nombreProducto, producto.getNombre());
	}
	
	@Test
	public void testPrecioAjustado() {
		agregados.add(lechuga);
		agregados.add(tomate);
		eliminados.add(cebolla);
		assertEquals(18000, producto.getPrecio());
	}
	
	@Test
	public void testGenerarTextoFactura() {
		agregados.add(lechuga);
		agregados.add(tomate);
		eliminados.add(cebolla);
		assertEquals("corral queso            16000\n    +lechuga                1000\n    +tomate                1000\n    -cebolla\n            18000\n", producto.generarTextoFactura());
		System.out.println(producto.generarTextoFactura());	
	}
	
}
