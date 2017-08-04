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
        File in = new File (PATHVISIO_BASEDIR, "testData/2017a/out2.gpml");
//        File in = new File (PATHVISIO_BASEDIR, "testData/WP248_2010a.gpml");
        assertTrue (in.exists());

        Pathway pwy = new Pathway();
        pwy.readFromXml(in, true);

        BiopaxExporter biopaxExporter = new BiopaxExporter(pwy);

        File tmp = new File (PATHVISIO_BASEDIR, "testData/2017a/WP206_90917.owl");

        biopaxExporter.export(tmp, true, 1);
    }

}