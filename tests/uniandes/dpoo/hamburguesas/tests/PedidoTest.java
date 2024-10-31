package uniandes.dpoo.hamburguesas.tests;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import uniandes.dpoo.hamburguesas.mundo.Ingrediente;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Producto;
import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class PedidoTest {
	private final String nombreCliente = "alejandro";
	private final String direccionCliente = "calle 70 #5-22";
	
	private final ArrayList<Ingrediente> agregados = new ArrayList<>();
	private final ArrayList<Ingrediente> eliminados = new ArrayList<>();
	
	private ProductoMenu productoMenu;
	private ProductoAjustado producto;
	
	
	private Pedido pedido = new Pedido(nombreCliente, direccionCliente);
	private Pedido pedido2 = new Pedido("ana", "calle 74");
	private Pedido pedido3 = new Pedido("jaime", "cra 5");
	
	
	@BeforeEach
	void setup() throws Exception{
		productoMenu = new ProductoMenu("todoterreno", 25000);
		producto = new ProductoAjustado(productoMenu, agregados, eliminados);
		pedido.agregarProducto(producto);

	}
	
	@Test
	public void testGetId() {
		assertEquals(1, pedido.getIdPedido());
		assertEquals(2, pedido2.getIdPedido());
		assertEquals(3, pedido3.getIdPedido());
	}
	
	@Test
	public void testGetNombre() {
		assertEquals(nombreCliente, pedido.getNombreCliente());
	}
	
	@Test
	public void testAgregarProducto() {

		assertTrue(pedido.getProductos().contains(producto));
	}
	 
	@Test
	public void testGetPrecioTotalPedido() {
		assertEquals(29750, pedido.getPrecioTotalPedido());
	}
	

	@Test
	public void testGenerarTextoFactura() {
		String texto = "Cliente: alejandro\nDirección: calle 70 #5-22\n----------------\ntodoterreno            25000\n            25000\n----------------\nPrecio Neto:  25000\nIVA:          4750\nPrecio Total: 29750\n";
		assertEquals(texto, pedido.generarTextoFactura());
	}
	 
	
	@Test
	public void testGuardarFactura() {
		
	}
}
