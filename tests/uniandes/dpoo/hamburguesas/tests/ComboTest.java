package uniandes.dpoo.hamburguesas.tests;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;

public class ComboTest {
	
	private final double descuento = 0.1;
	private final String nombreCombo = "combo corral";
	private final ArrayList<ProductoMenu> items = new ArrayList<>();
	
	private ProductoMenu corral = new ProductoMenu("corral", 14000);
	private ProductoMenu papas = new ProductoMenu("papas medianas", 5500);
	private ProductoMenu gaseosa = new ProductoMenu("gaseosa", 5000);

	private Combo combo;
		
	@BeforeEach
	void setup() throws Exception{
		items.add(corral);
		items.add(papas);
		items.add(gaseosa);
		combo = new Combo(nombreCombo, descuento, items);
	}
	
	@Test
	public void testNombreCombo() {
		assertEquals("combo corral", combo.getNombre());
	}
	
	@Test
	public void testGetPrecio() {
		assertEquals(22050, combo.getPrecio());
	}
	
	@Test
	public void testGenerarTextoFactura() {
		assertEquals("Combo combo corral\n Descuento: 0.1\n            22050\n", combo.generarTextoFactura());
	}
}
 