/**
 * Find Security Bugs
 * Copyright (c) Philippe Arteau, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package com.h3xstream.findsecbugs.jsp;

import com.h3xstream.findbugs.test.BaseDetectorTest;
import com.h3xstream.findbugs.test.EasyBugReporter;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Before running theses tests cases, jsp files need to be compiled.
 *
 * <pre>mvn clean test-compile</pre>
 */
public class JspXssDetectorTest extends BaseDetectorTest {

    @Test
    public void detectXssDirectUse() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_1_direct_use.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        //Assertions
        for (Integer line : Arrays.asList(10, 15)) { //Generated classes lines doesn't match original JSP
            verify(reporter).doReportBug(
                    bugDefinition()
                            .bugType("XSS_JSP_PRINT")
                            .inJspFile("xss/xss_1_direct_use.jsp")
                            .atJspLine(line)
                            .build()
            );
        }

        verify(reporter, times(2)).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

    @Test
    public void detectXssTransferLocal() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_2_transfer_local.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        //Assertions
        //Generated classes lines doesn't match original JSP
        verify(reporter).doReportBug(
                bugDefinition()
                        .bugType("XSS_JSP_PRINT")
                        .inJspFile("xss/xss_2_transfer_local.jsp")
                        .atJspLine(10)
                        .build()
        );

        verify(reporter).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

    @Test
    public void detectXssFalsePositiveSafeInput() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_3_false_positive_static_function.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        //No alert should be trigger
        verify(reporter, times(0)).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

    @Test
    public void detectXssFalsePositiveOverwriteLocal() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_4_false_positive_overwrite_local.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        //No alert should be trigger
        verify(reporter, times(0)).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

    @Test
    public void detectXssMultipleTransfertLocal() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_5_multiple_transfer_local.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        verify(reporter).doReportBug(
                bugDefinition()
                        .bugType("XSS_JSP_PRINT")
                        .inJspFile("xss/xss_5_multiple_transfer_local.jsp")
                        .atJspLine(13)
                        .build()
        );

        verify(reporter).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

    @Test
    public void detectXssGetParameter() throws Exception {
        //Locate test code
        String[] files = {
                getJspFilePath("xss/xss_6_get_parameter.jsp")
        };

        //Run the analysis
        EasyBugReporter reporter = spy(new EasyBugReporter());
        analyze(files, reporter);

        verify(reporter).doReportBug(
                bugDefinition()
                        .bugType("XSS_JSP_PRINT")
                        .inJspFile("xss/xss_6_get_parameter.jsp")
                        .atJspLine(7)
                        .build()
        );

        verify(reporter).doReportBug(bugDefinition().bugType("XSS_JSP_PRINT").build());
    }

}

