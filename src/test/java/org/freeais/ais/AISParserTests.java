package org.freeais.ais;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

/**
 * Tests for the <code>AISParser</code> class.
 *
 * @author Dr. Jesper Zedlitz  jesper@zedlitz.de
 */
public class AISParserTests {

    private AISParser aisParser = new AISParser();

    /**
     * Parse a type 1 message.
     */
    @Test
    public void AISPositionA() throws AISParseException {

        long timeBefore = System.currentTimeMillis();
        AISPositionA result = (AISPositionA) aisParser.parse("!AIVDM,1,1,,B,139O<l0P000dtRPO6lb@0?wb061l,0*35");
        long timeAfter = System.currentTimeMillis();

        assertEquals(1, result.getMsgId());
        assertEquals(211274960, result.getMmsi());
        assertEquals(9.8185867, result.getLongitude(), 0.001);
        assertEquals(54.362735, result.getLatitude(), 0.001);

        assertTrue( timeBefore <= result.getMsgTimestamp().getTime());
        assertTrue( timeAfter >= result.getMsgTimestamp().getTime());
    }

    /**
     * Process a <code>AISVessel</code> message consisting of two input strings.
     */
    @Test
    public void AISVessel() throws AISParseException {

        Object firstResult = aisParser.parse("!AIVDM,2,1,3,A,539O<l000000@CO77>1<DPE=@DAB2222222222001P72340Ht51QDTVD,0*1A");
        AISVessel result = (AISVessel) aisParser.parse("!AIVDM,2,2,3,A,3jCU830CQ5iB4`0,2*0B");

        assertNull(firstResult);
        assertNotNull(result);
        assertEquals(5, result.getMsgId());
        assertEquals(211274960, result.getMmsi());
        assertEquals("SEHESTEDT", result.getName());
        assertEquals(0, result.getShipType());
        assertEquals(12, result.getDimensionA());
        assertEquals(7, result.getDimensionB());
        assertEquals(2, result.getDimensionC());
        assertEquals(3, result.getDimensionD());
        assertEquals("FERRYPOINT LANDWEHR", result.getDestination());
        assertEquals(211, result.getCountryId());
    }

    /**
     * An incomplete message (part 1 of a two part message) will lead to a <code>null</code> result.
     */
    @Test
    public void incompleteMessage() throws AISParseException {
        IAISMessage result = aisParser.parse("!AIVDM,2,1,3,A,539O<l000000@CO77>1<DPE=@DAB2222222222001P72340Ht51QDTVD,0*1A");
        assertNull(result);
    }

    /**
     * Parse a number of real AIS messages received.
     */
    @Test
    public void realData() throws AISParseException, IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/real-data.txt")));
        String line = in.readLine();
        while (line != null) {
            aisParser.parse(line);
            line = in.readLine();
        }
    }
}
