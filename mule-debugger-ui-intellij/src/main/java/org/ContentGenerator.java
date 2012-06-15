/**
 *
 * (c) 2012 MuleSoft, Inc. This software is protected under international copyright
 * law. All use of this software is subject to MuleSoft's Master Subscription Agreement
 * (or other master license agreement) separately entered into in writing between you and
 * MuleSoft. If such an agreement is not in place, you may not use the software.
 */
package org;

import java.io.FileWriter;
import java.io.IOException;

public class ContentGenerator {
    public static void main(String[] args) throws IOException {

        int items = 100000;
        FileWriter fileWriter = new FileWriter("/Users/machaval/labs/dm-projects/items-" + items + ".json", true);
        fileWriter.write("{\n" +
                "  \"items\" : [");
        for (int i = 0; i < items; i++) {
            StringBuilder line = new StringBuilder();
            line.append("{\n" + "    \"author\" : \"Alberto Pose_").append(i).append("\",\n")
                    .append("    \"description\" : \"Corcel autobiography_").append(i).append("\",\n")
                    .append("    \"name\" : \"My life_").append(i).append("\",\n")
                    .append("    \"vote\" : ").append(i).append("\n")
                    .append("  }");
            if(i < items -1){
                line.append(",");
            }
            fileWriter.write(line.toString());
            fileWriter.flush();
        }
        fileWriter.write("]\n" +
                "}");


        fileWriter.flush();
        fileWriter.close();


    }
}
