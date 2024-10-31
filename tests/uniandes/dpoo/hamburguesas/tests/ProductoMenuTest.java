package uniandes.dpoo.hamburguesas.tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;



public class ProductoMenuTest {
	
	private final String nombreProducto = "corral";
	private final int precioProductoMenu = 14000;
	
	private ProductoMenu producto;
	
	@BeforeEach
	void setup() throws Exception{
		producto = new ProductoMenu(nombreProducto, precioProductoMenu);
	}
	
	@Test
	public void testNombre() {
		assertEquals("corral", producto.getNombre());
	}
	
	@Test
	public void testPrecioBase() {
		assertEquals(14000, producto.getPrecio());
	}
	
	@Test
	public void testGenerarTextoFactura() {
		assertEquals("corral\n            14000\n", producto.generarTextoFactura());
	}
}
 