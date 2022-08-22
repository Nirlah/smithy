/*
 * Copyright 2022 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.smithy.rulesengine.reterminus.lang.rule;

import static software.amazon.smithy.rulesengine.reterminus.error.RuleError.ctx;

import software.amazon.smithy.model.node.Node;
import software.amazon.smithy.model.node.ObjectNode;
import software.amazon.smithy.rulesengine.reterminus.eval.Scope;
import software.amazon.smithy.rulesengine.reterminus.eval.Type;
import software.amazon.smithy.rulesengine.reterminus.lang.expr.Expr;
import software.amazon.smithy.rulesengine.reterminus.util.StringUtils;
import software.amazon.smithy.rulesengine.reterminus.visit.RuleValueVisitor;

public class ErrorRule extends Rule {
    private final Expr error;

    public ErrorRule(Rule.Builder builder, Expr error) {
        super(builder);
        this.error = error;
    }

    @Override
    public <T> T accept(RuleValueVisitor<T> visitor) {
        return visitor.visitErrorRule(this.error);
    }

    @Override
    protected Type typecheckValue(Scope<Type> scope) {
        return ctx("while typechecking the error", error, () -> error.typecheck(scope).expectString());
    }

    @Override
    public Node toNode() {
        ObjectNode.Builder on = super.toNode().expectObjectNode().toBuilder();
        on.withMember(Rule.ERROR, error);
        return on.build();
    }

    @Override
    void withValueNode(ObjectNode.Builder builder) {
        builder.withMember("error", error.toNode()).withMember(TYPE, ERROR);
    }

    @Override
    public String toString() {
        return super.toString()
               + StringUtils.indent(String.format("error(%s)", error), 2);
    }

    public Expr getError() {
        return error;
    }
}