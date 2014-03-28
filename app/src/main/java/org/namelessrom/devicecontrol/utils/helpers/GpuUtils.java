package org.namelessrom.devicecontrol.utils.helpers;

import org.namelessrom.devicecontrol.utils.Utils;
import org.namelessrom.devicecontrol.utils.constants.PerformanceConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GpuUtils implements PerformanceConstants {

    public static String[] getFreqToMhz(final String file) throws IOException {
        final ArrayList<String> names = new ArrayList<String>();
        Utils.setPermissions(file);

        final File freqfile = new File(file);
        FileInputStream fin1 = null;
        String s1 = null;
        try {
            fin1 = new FileInputStream(freqfile);
            byte fileContent[] = new byte[(int) freqfile.length()];
            fin1.read(fileContent);
            s1 = new String(fileContent);
        } finally {
            if (fin1 != null) {
                fin1.close();
            }
        }
        final String[] frequencies = s1.trim().split(" ");
        for (final String s : frequencies) {
            names.add(toMhz(s));
        }
        return names.toArray(new String[names.size()]);
    }

    public static String toMhz(final String mhz) {
        return (String.valueOf(Integer.parseInt(mhz) / 1000000) + " MHz");
    }

    public static void restore() {
        final StringBuilder sb = new StringBuilder();

        final String gpuMax = PreferenceHelper.getString(PREF_MAX_GPU, "");
        if (gpuMax != null && !gpuMax.isEmpty()) {
            Utils.setPermissions(GPU_MAX_FREQ_FILE);
            sb.append("busybox echo ").append(gpuMax).append(" > ").append(GPU_MAX_FREQ_FILE);
        }

        Utils.runRootCommand(sb.toString());
    }

}
