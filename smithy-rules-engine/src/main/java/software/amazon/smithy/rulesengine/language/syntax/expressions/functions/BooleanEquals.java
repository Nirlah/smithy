/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 * SPDX-License-Identifier: Apache-2.0
 */

package software.amazon.smithy.rulesengine.language.syntax.expressions.functions;

import java.util.Arrays;
import java.util.List;
import software.amazon.smithy.rulesengine.language.evaluation.type.Type;
import software.amazon.smithy.rulesengine.language.evaluation.value.Value;
import software.amazon.smithy.rulesengine.language.syntax.expressions.ExpressionVisitor;
import software.amazon.smithy.utils.SmithyUnstableApi;

/**
 * Represents a two argument function that compares two expression for boolean equality.
 */
@SmithyUnstableApi
public final class BooleanEquals extends LibraryFunction {
    public static final String ID = "booleanEquals";
    private static final Definition DEFINITION = new Definition();

    private BooleanEquals(FunctionNode functionNode) {
        super(DEFINITION, functionNode);
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visitBoolEquals(functionNode.getArguments().get(0), functionNode.getArguments().get(1));
    }

    /**
     * A {@link FunctionDefinition} for the {@link BooleanEquals} function.
     */
    public static final class Definition implements FunctionDefinition {
        @Override
        public String getId() {
            return ID;
        }

        @Override
        public List<Type> getArguments() {
            return Arrays.asList(Type.booleanType(), Type.booleanType());
        }

        @Override
        public Type getReturnType() {
            return Type.booleanType();
        }

        @Override
        public Value evaluate(List<Value> arguments) {
            // Specialized in the ExpressionVisitor, so this doesn't need an implementation.
            return null;
        }

        @Override
        public BooleanEquals createFunction(FunctionNode functionNode) {
            return new BooleanEquals(functionNode);
        }
    }
}
