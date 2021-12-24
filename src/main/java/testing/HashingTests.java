package testing;

import application.methods.PBKDF2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

class HashingTests {
    @Test
    void test() throws UnsupportedEncodingException {
        Assertions.assertEquals("f1f9fbc32963804be08ac9d3ab4850aafa5dbae464646940c30c652cc5b47deee164747a284ef1202d518c948d76ff7bed90771a909b25fd718cb3a6a56a6785", PBKDF2.getHashedPassword("testUnitar1"), "test de hashing");
        Assertions.assertEquals("c7039983aeaac4232ea63ef9d05404c8aba51a1e52578b30d90d4f463b65eb1a8dde9bb856a06b1dfe0011a378b5c913fbd7b1a9dbeff33626bd4ea0a4121eae", PBKDF2.getHashedPassword("testUnitar2"), "test de hashing");
        Assertions.assertEquals("ebfc7a1f4bfa951ce31b24391412d8f21e89a7a7fb325ff10b2f8b5f64210f14f18173061fec7ebab58b9b4a2e158c4c86dd1d2b46754da38e170affab40a217", PBKDF2.getHashedPassword("testUnitar3"), "test de hashing");
        Assertions.assertEquals("b0cbf61117307a3377082d47a263ce0ac359d51430b8cbbb2012dce41b731d1586c3afa1665d35dd4d8f0e9cd9e16ef5d7e2f65013a11ddd53cc837d5348fa78", PBKDF2.getHashedPassword("testUnitar4"), "test de hashing");
        Assertions.assertEquals("ab6a62473d04774c33d4742d385c099b827db9131a57db6090fe0dda263e78b460c095bcad808044918c896d31b09a8d5c7c3d0a54bbc0656a85cb20c3ba6cf1", PBKDF2.getHashedPassword("testUnitar5"), "test de hashing");
        Assertions.assertEquals("dc3a8d7e97e66a30cd0ee82eafae740e3733657af046f4fcb7fb94faa0ec6f790c953ba6a621cfa7afa4bdd675b7db7ed6e9dd4f4b4c25b109db5975d00c23aa", PBKDF2.getHashedPassword("qwertyuiop"), "test de hashing");
        Assertions.assertEquals("e952189907f8cc59975b4f9c71757df00c8449628e8837307ba243d92d474af9addcd2c295467f8237dea5c31a4747720efcb372c13c9a63c410c773e8141cf3", PBKDF2.getHashedPassword("aewhreh8932qy97832c8bicewrgcaurugyarguyaerxgueawgyrxgyag"), "test de hashing");
    }
}