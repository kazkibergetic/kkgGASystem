package problems.RoughSets.bireduct;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * IndexRegistryTest.
 *
 * @author Maxim Rybakov
 */
public class IndexRegistryTest {
    private IndexRegistry registry = new IndexRegistry();

    @Test
    public void testNoMappings() {
        assertEquals(0, registry.getOriginalIndex(0));
        assertEquals(1, registry.getOriginalIndex(1));
        assertEquals(2, registry.getOriginalIndex(2));
        assertEquals(0, registry.getCurrentIndex(0));
        assertEquals(1, registry.getCurrentIndex(1));
        assertEquals(2, registry.getCurrentIndex(2));
    }

    @Test
    public void testSimpleMappingsLeft() {
        registry.registerMapping(0, 1);
        registry.registerMapping(0, 2);

        assertEquals(2, registry.getOriginalIndex(0));
        assertEquals(0, registry.getOriginalIndex(1));
        assertEquals(1, registry.getOriginalIndex(2));
        assertEquals(3, registry.getOriginalIndex(3));
        assertEquals(4, registry.getOriginalIndex(4));
        assertEquals(1, registry.getCurrentIndex(0));
        assertEquals(2, registry.getCurrentIndex(1));
        assertEquals(0, registry.getCurrentIndex(2));
        assertEquals(3, registry.getCurrentIndex(3));
        assertEquals(4, registry.getCurrentIndex(4));
    }

    @Test
    public void testDirectMappings() {
        registry.registerMapping(0, 1);
        registry.registerMapping(0, 2);
        registry.registerOneDirectMapping(3, 0);

        assertEquals(2, registry.getOriginalIndex(0));
        assertEquals(0, registry.getOriginalIndex(1));
        assertEquals(1, registry.getOriginalIndex(2));
        assertEquals(2, registry.getOriginalIndex(3));
        assertEquals(1, registry.getCurrentIndex(0));
        assertEquals(2, registry.getCurrentIndex(1));
        assertEquals(0, registry.getCurrentIndex(2));
        assertEquals(3, registry.getCurrentIndex(3));
    }

    @Test
    public void testSimpleMappingsRight() {
        registry.registerMapping(0, 1);
        registry.registerMapping(2, 1);

        assertEquals(1, registry.getOriginalIndex(0));
        assertEquals(2, registry.getOriginalIndex(1));
        assertEquals(0, registry.getOriginalIndex(2));
        assertEquals(3, registry.getOriginalIndex(3));
        assertEquals(4, registry.getOriginalIndex(4));
        assertEquals(2, registry.getCurrentIndex(0));
        assertEquals(0, registry.getCurrentIndex(1));
        assertEquals(1, registry.getCurrentIndex(2));
        assertEquals(3, registry.getCurrentIndex(3));
        assertEquals(4, registry.getCurrentIndex(4));
    }

    @Test
    public void testSeriesMappings() {
        registry.registerMapping(0, 1);
        registry.registerMapping(2, 0);
        registry.registerMapping(1, 0);
        registry.registerMapping(3, 1);
        registry.registerMapping(1, 2);
        registry.registerMapping(2, 3);

        assertEquals(0, registry.getOriginalIndex(0));
        assertEquals(1, registry.getOriginalIndex(1));
        assertEquals(2, registry.getOriginalIndex(2));
        assertEquals(3, registry.getOriginalIndex(3));
        assertEquals(4, registry.getOriginalIndex(4));
        assertEquals(0, registry.getCurrentIndex(0));
        assertEquals(1, registry.getCurrentIndex(1));
        assertEquals(2, registry.getCurrentIndex(2));
        assertEquals(3, registry.getCurrentIndex(3));
        assertEquals(4, registry.getCurrentIndex(4));
    }
}
