package org.pathvisio.core.exporter;

import junit.framework.TestCase;
import org.pathvisio.core.model.Pathway;

import java.io.File;

/**
 * Created by saurabhk351 on 25/06/2017.
 */
public class ExportHelperTest extends TestCase {
    private static final File PATHVISIO_BASEDIR = new File (".");

    public void testExport() throws Exception {
        File in = new File (PATHVISIO_BASEDIR, "testData/2010a/biopax-opencontrolledvocabulary-testcase.gpml");
        assertTrue (in.exists());

        Pathway pwy = new Pathway();
        pwy.readFromXml(in, true);

        ExportHelper exportHelper = new ExportHelper(pwy);

        File tmp = new File (PATHVISIO_BASEDIR, "testData/2017a/biopax-opencontrolledvocabulary-testcase.owl");

        exportHelper.export(tmp, true);
    }

}