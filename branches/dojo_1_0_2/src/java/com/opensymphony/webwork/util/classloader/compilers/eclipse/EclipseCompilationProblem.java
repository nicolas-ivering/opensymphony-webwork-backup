/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opensymphony.webwork.util.classloader.compilers.eclipse;

import com.opensymphony.webwork.util.classloader.problems.CompilationProblem;
import org.eclipse.jdt.core.compiler.IProblem;

public class EclipseCompilationProblem implements CompilationProblem {

    private final IProblem problem;

    public EclipseCompilationProblem(final IProblem pProblem) {
        problem = pProblem;
    }

    public boolean isError() {
        return problem.isError();
    }

    public String getFileName() {
        return new String(problem.getOriginatingFileName());
    }

    public int getStartLine() {
        return problem.getSourceLineNumber();
    }

    public int getStartColumn() {
        return problem.getSourceStart();
    }

    public int getEndLine() {
        return getStartLine();
    }

    public int getEndColumn() {
        return problem.getSourceEnd();
    }

    public String getMessage() {
        return problem.getMessage();
    }

    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(getFileName()).append(" (");
        sb.append(getStartLine());
        sb.append(":");
        sb.append(getStartColumn());
        sb.append(") : ");
        sb.append(getMessage());
        return sb.toString();
    }

    public int getId() {
        return problem.getID();
    }

}
