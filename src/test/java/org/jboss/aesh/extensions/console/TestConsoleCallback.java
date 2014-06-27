/*
 * Copyright 2012 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jboss.aesh.extensions.console;

import org.jboss.aesh.console.AeshConsoleCallback;
import org.jboss.aesh.console.ConsoleOperation;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public abstract class TestConsoleCallback extends AeshConsoleCallback {
    private CountDownLatch latch;
    private List<Throwable> exceptions;

    public TestConsoleCallback(CountDownLatch latch, List<Throwable> exceptions) {
        this.latch = latch;
        this.exceptions = exceptions;
    }

    @Override
    public final int execute(ConsoleOperation output) throws InterruptedException {
        try {
            return verify(output);
        } catch (Throwable e) {
            exceptions.add(e);
            throw e;
        } finally {
            latch.countDown();
        }
    }

    public abstract int verify(ConsoleOperation output);
}