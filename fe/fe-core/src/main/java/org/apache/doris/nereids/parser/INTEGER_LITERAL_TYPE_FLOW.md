# Integer Literal Type Inference Flow (Nereids)

This document describes where and how Nereids determines integer literal types,
using `abs(111111111)` as a concrete example.

## Flowchart

```mermaid
flowchart TD
    A[SQL text<br/>abs(111111111)] --> B[ANTLR parse rule: number -> integerLiteral]
    B --> C[LogicalPlanBuilder.visitIntegerLiteral(ctx)]
    C --> D{BigInteger range check}

    D -->|fits tinyint| T[TinyIntLiteral]
    D -->|fits smallint| S[SmallIntLiteral]
    D -->|fits int| I[IntegerLiteral]
    D -->|fits bigint| BI[BigIntLiteral]
    D -->|fits largeint| L[LargeIntLiteral]
    D -->|out of largeint range| DV3[DecimalV3Literal]

    I --> E[Function binding by Abs signatures]
    BI --> E
    L --> E
    DV3 --> E

    E --> F[Constant folding executable impl (NumericArithmetic.abs(...))]
```

## Key locations in code

1. Grammar identifies integer literal (`#integerLiteral`).
2. `LogicalPlanBuilder.visitIntegerLiteral` performs range-based literal type creation.
3. `Abs.SIGNATURES` chooses matching function signature by argument type.
4. `NumericArithmetic.abs(...)` executes per-literal constant folding implementation.

See inline comments tagged with:

- `INTEGER_LITERAL_TYPE_FLOW step-1`
- `INTEGER_LITERAL_TYPE_FLOW step-2`
- `INTEGER_LITERAL_TYPE_FLOW step-3`
- `INTEGER_LITERAL_TYPE_FLOW step-4`
