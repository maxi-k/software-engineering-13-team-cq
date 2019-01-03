import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

public class Hehe {

    @Test
    public void generate() {
        for (int i = 3000; i < 3500; i++) {
            System.out.println(String.format("  - WBAXXX%011d", i));
        }
    }
}
