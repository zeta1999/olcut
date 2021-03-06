package com.oracle.labs.mlrg.olcut.provenance;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.oracle.labs.mlrg.olcut.config.AllFieldsConfigurable;
import com.oracle.labs.mlrg.olcut.config.ConfigurationManager;
import com.oracle.labs.mlrg.olcut.config.PropertySheet;
import com.oracle.labs.mlrg.olcut.util.IOUtil;

public class ProvenanceUtilTest {

    @BeforeAll
    public static void setup() {
        Logger logger = Logger.getLogger(PropertySheet.class.getName());
        logger.setLevel(Level.SEVERE);
    }

    @Test
    void testSerialize() throws Exception {
        File tempFile = File.createTempFile("serialized-provenancable", ".ser", new File("target"));
        tempFile.deleteOnExit();

        ConfigurationManager cm = new ConfigurationManager("allConfig.xml");
        AllFieldsConfigurable afc = (AllFieldsConfigurable) cm.lookup("all-config");
        cm.close();
        MyProvenancableClass mpc = new MyProvenancableClass(afc);
        IOUtil.serialize(mpc, tempFile.getPath());
        mpc = IOUtil.deserialize(tempFile.getPath(), MyProvenancableClass.class).get();
        assertEquals(afc, mpc.afc);
    }

    public static class MyProvenancableClass implements Serializable {
        private static final long serialVersionUID = 1L;
        public AllFieldsConfigurable afc;

        public MyProvenancableClass(AllFieldsConfigurable afc) {
            super();
            this.afc = afc;
        }

        private void readObject(ObjectInputStream inputStream) throws ClassNotFoundException, IOException {
            this.afc = (AllFieldsConfigurable) ProvenanceUtil.readObject(inputStream);
        }

        private void writeObject(ObjectOutputStream outputStream) throws IOException {
            ProvenanceUtil.writeObject(this.afc, outputStream);
        }
    }
}
