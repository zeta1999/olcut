package com.oracle.labs.mlrg.olcut.config.edn;

import com.oracle.labs.mlrg.olcut.config.ConfigurationManager;
import com.oracle.labs.mlrg.olcut.config.ListConfig;
import com.oracle.labs.mlrg.olcut.config.PropertyException;
import com.oracle.labs.mlrg.olcut.config.SetConfig;
import com.oracle.labs.mlrg.olcut.config.StringConfigurable;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 *
 */
public class GenericConfigTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
        ConfigurationManager.addFileFormatFactory(new EdnConfigFactory());
    }

    @Test
    public void correctListConfig() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("genericConfig.edn");
        ListConfig s = (ListConfig) cm.lookup("correctListConfig");

        Assert.assertEquals("StringList has an incorrect number of values", 4,s.stringList.size());
        Assert.assertTrue("StringList missing values",s.stringList.contains("5.5"));
        Assert.assertTrue("StringList missing values",s.stringList.contains("3.14"));
        Assert.assertTrue("StringList missing values",s.stringList.contains("2.79"));
        Assert.assertFalse("StringList contains incorrect value",s.stringList.contains("1000000.0"));

        Assert.assertEquals("DoubleList has an incorrect number of values", 4,s.doubleList.size());
        Assert.assertTrue("DoubleList missing values",s.doubleList.contains(5.5));
        Assert.assertTrue("DoubleList missing values",s.doubleList.contains(3.14));
        Assert.assertTrue("DoubleList missing values",s.doubleList.contains(2.79));
        Assert.assertFalse("DoubleList contains incorrect values",s.doubleList.contains(1000000.0));

        Assert.assertEquals("StringConfigList has an incorrect number of values", 2,s.stringConfigurableList.size());
        Assert.assertTrue("StringConfigList missing values",s.stringConfigurableList.contains(new StringConfigurable("dragons","wyverns","wyrms")));
        Assert.assertTrue("StringConfigList missing values",s.stringConfigurableList.contains(new StringConfigurable("jedi","sith","scoundrels")));
        Assert.assertFalse("StringConfigList contains incorrect values",s.stringConfigurableList.contains(new StringConfigurable("sheep","cows","pigs")));
    }

    @Test(expected=PropertyException.class)
    public void incorrectListConfig() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("genericConfig.edn");
        ListConfig l = (ListConfig) cm.lookup("incorrectListConfig");
    }

    @Test
    public void correctSetConfig() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("genericConfig.edn");
        SetConfig s = (SetConfig) cm.lookup("correctSetConfig");

        Assert.assertEquals("StringSet has an incorrect number of values", 3,s.stringSet.size());
        Assert.assertTrue("StringSet missing values",s.stringSet.contains("5.5"));
        Assert.assertTrue("StringSet missing values",s.stringSet.contains("3.14"));
        Assert.assertTrue("StringSet missing values",s.stringSet.contains("2.79"));
        Assert.assertFalse("StringSet contains incorrect value",s.stringSet.contains("1000000.0"));

        Assert.assertEquals("DoubleSet has an incorrect number of values", 3,s.doubleSet.size());
        Assert.assertTrue("DoubleSet missing values",s.doubleSet.contains(5.5));
        Assert.assertTrue("DoubleSet missing values",s.doubleSet.contains(3.14));
        Assert.assertTrue("DoubleSet missing values",s.doubleSet.contains(2.79));
        Assert.assertFalse("DoubleSet contains incorrect values",s.doubleSet.contains(1000000.0));

        Assert.assertEquals("StringConfigSet has an incorrect number of values", 2,s.stringConfigurableSet.size());
        Assert.assertTrue("StringConfigSet missing values",s.stringConfigurableSet.contains(new StringConfigurable("dragons","wyverns","wyrms")));
        Assert.assertTrue("StringConfigSet missing values",s.stringConfigurableSet.contains(new StringConfigurable("jedi","sith","scoundrels")));
        Assert.assertFalse("StringConfigSet contains incorrect values",s.stringConfigurableSet.contains(new StringConfigurable("sheep","cows","pigs")));
    }

    @Test(expected=PropertyException.class)
    public void incorrectSetConfig() throws IOException {
        ConfigurationManager cm = new ConfigurationManager("genericConfig.edn");
        SetConfig s = (SetConfig) cm.lookup("incorrectSetConfig");
    }
}