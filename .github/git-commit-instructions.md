# Git Conventional Commit Instructions

All commit messages MUST follow the Conventional Commits specification to ensure clarity and consistency across the project.

Specification: https://www.conventionalcommits.org/en/v1.0.0/

## Commit Message Template

The following template must be used for all commit messages. Elements marked as optional can be omitted if not applicable.

```
<type>[optional scope][optional !]: <description>

[optional body]

[optional footer(s)]
```

- **type** (REQUIRED): lowercase noun such as `feat`, `fix`, `docs`, etc.
  - The type `feat` MUST be used when a commit adds a new feature.
  - The type `fix` MUST be used when a commit represents a bug fix.
- **scope** (OPTIONAL): a noun in parentheses describing *where* the change is, e.g. `fix(build): ...`.
- **!** (OPTIONAL): put `!` immediately before `:` to mark a breaking change, e.g. `feat(api)!: ...`.
- **description** (REQUIRED): short summary *immediately* after `: `.
  - Prefer <= 50 characters; avoid a trailing period.
- **body** (OPTIONAL): Detailed explanation of the changes
  - MUST start after **one blank line**.
  - MUST be wrapped at 72 characters.
  - MUST use bullet points (`*`) to list multiple items for clarity. Multiple paragraphs are NOT allowed; always use bullet points for multiple items.
  - Explain the reasoning behind the changes and any relevant context (e.g., to improve performance, fix an issue, enhance user experience).
  - This section can be omitted if the `description` is self-explanatory.
- **footer** (OPTIONAL): An optional section for additional metadata with one or more lines.
  - MUST start after **one blank line** following the body (or following the header if the `body` is empty).

## Types

Conventional Commits only mandates `feat` and `fix` semantics; other types are allowed.

- `feat`: new feature or behavior change (MINOR)
- `fix`: bug fix (PATCH)
- `docs`: documentation only
- `refactor`: refactor without behavior change
- `test`: add/adjust tests only
- `chore`: maintenance (build tooling, deps, formatting, etc.)
- `style`: formatting-only changes (no logic)

If unsure, prefer `fix` (user-facing bug) vs `refactor` (no behavior change).

## Scopes (recommended)

Use a scope when it clarifies the changed area. Keep it short and consistent.
Prefer one of:

- `config`
- `build`
- `dependencies`
- `test`
- `docs`

Examples: `feat(config): ...`, `fix(build): ...`, `docs(dependencies): ...`.

## Breaking changes

Mark breaking changes in ONE of these ways:

1. Add `!` in the header (preferred for visibility):

   - `feat(api)!: change response shape`

2. Add a footer trailer:

   - `BREAKING CHANGE: <what broke and how to migrate>`

Notes:
- `BREAKING CHANGE` MUST be uppercase.
- If you use `!`, the footer MAY be omitted when the description explains the breaking change. Use the footer when migration steps aren’t obvious.

## Footer

Footers follow a trailer-like format:

- `Token: value` (colon + space)
- or `Token #value` (space + `#`)

Rules:
- Footer tokens MUST NOT contain spaces. Use `-` instead (e.g. `Reviewed-by`).
- Multiple footers are allowed.
- Common tokens you may use: `Refs`, `Closes`, `Fixes`, `Reviewed-by`.

## Examples

**Feature (with scope):**
```
feat(tools): add datasource lookup tool

Expose a new MCP tool that queries UCP data sources by name.

Refs: #123
```

**Bug fix (empty body):**
```
fix(resources): correct template path resolution
```

**Refactor:**
```
refactor(infrastructure): extract common HTTP client config

Centralize timeouts and retry policy to reduce duplication across services.
```

**Docs:**
```
docs(readme): document local dev setup
```

**Breaking change (using ! + footer):**
```
feat(tools)!: rename getDataSources tool

The tool is now exposed as `listDataSources` for consistency.

BREAKING CHANGE: Clients must call `listDataSources` instead of `getDataSources`.
```

**Multiple footers:**
```
fix(infrastructure): prevent racing of upstream requests

Introduce a request id and ignore responses not matching the latest request.

Reviewed-by: John Wick
Refs: #456
```

## Additional Guidelines (Important)

- **Do** use imperative mood in the `description` (e.g., "Fix bug" instead of "Fixed bug" or "Fixes bug").
- **Do** use the `body` to explain what and why, rather than how.
- **Do** keep the first line specific — avoid vague messages like "update" or "fix" without context.
- **Do not** assume anything not explicitly stated in the commit message; provide sufficient context for future reference.
- **Do not** end the `description` with a dot (`.`).

## Validation Checklist

Every instruction in this document is represented below as a single checkable item.

- [ ] Message follows the Conventional Commits v1.0.0 spec.
- [ ] Message uses the template structure: `header`, optional `body`, optional `footer(s)`.
- [ ] Header uses the required `: ` delimiter between `<type>(<scope>)!` and `<description>`.
- [ ] `type` is present.
- [ ] `type` is lowercase.
- [ ] `type` is a noun such as `feat`, `fix`, `docs`, `refactor`, `test`, `chore`, `style`.
- [ ] If the change is a new feature, `type` is `feat`.
- [ ] If the change is a bug fix, `type` is `fix`.
- [ ] If a scope is used, it is in parentheses immediately after `type` (e.g., `fix(build): ...`).
- [ ] If a scope is used, it describes *where* the change is.
- [ ] If a scope is used, it is short and consistent.
- [ ] If a scope is used, it preferably matches one of: `config`, `build`, `dependencies`, `test`, `docs`.
- [ ] If the change is breaking and `!` is used, `!` appears immediately before `:` in the header.
- [ ] `description` is present and appears immediately after `: `.
- [ ] `description` is specific (not vague like “update” or “fix”).
- [ ] `description` is written in imperative mood.
- [ ] `description` is <= 50 characters (preferred).
- [ ] `description` does not end with a period (`.`).
- [ ] If a body is present, it starts after exactly one blank line following the header.
- [ ] If a body is present, lines are wrapped at 72 characters.
- [ ] If multiple points are needed in the body, use `*` bullet points for clarity. Multiple paragraphs are NOT allowed.
- [ ] If a body is present, it explains what/why (reasoning + context), not just how.
- [ ] If the description is self-explanatory, the body is omitted (allowed).
- [ ] If footer(s) are present, they start after exactly one blank line after the body (or after the header when the body is empty).
- [ ] Each footer line matches a trailer format: `Token: value` or `Token #value`.
- [ ] Footer tokens contain no spaces (use `-` instead, e.g., `Reviewed-by`).
- [ ] If multiple footers are needed, include multiple footer lines (allowed).
- [ ] If the change is breaking, it is marked either by `!` in the header or by a `BREAKING CHANGE:` footer.
- [ ] If using a breaking-change footer, the token is exactly `BREAKING CHANGE:` (uppercase).
- [ ] If using `!` and the breaking change isn’t obvious, include a `BREAKING CHANGE:` footer with migration info.
- [ ] Commit message is clear, specific, and proofread.
- [ ] If unsure whether the change is user-facing bug fix vs pure refactor, prefer `fix` over `refactor`.

## Common Mistakes to Avoid

Review this list to avoid frequent errors and ensure your commit message meets project standards:

- **Not following Conventional Commits at all** — breaks tooling and release automation.
- **Not using the required template structure** — header first; body/footers only if needed.
- **Missing the required `: ` delimiter in the header** — e.g., `feat(scope) add ...`.
- **Using an invalid header structure** — wrong separator, missing parentheses, wrong placement of `!`.
- **Omitting the `type`** — e.g., `: add thing`.
- **Using an uppercase or mixed-case `type`** — e.g., `Feat:`.
- **Using an unsupported or unclear `type`** — hurts changelog/release semantics.
- **Using `feat` for a non-feature or failing to use `feat` for a feature** — breaks changelog and release logic.
- **Using `fix` for a non-bugfix or failing to use `fix` for a bugfix** — breaks changelog and release logic.
- **Putting the scope in the wrong place or format** — e.g., `feat: (api) ...`.
- **Using a scope that doesn’t describe where the change is** — or using scopes inconsistently/too long.
- **Forgetting to mark breaking changes** — no `!` and no `BREAKING CHANGE:` footer.
- **Putting `!` anywhere other than immediately before `:`** — e.g., `feat!(api): ...`.
- **Using a breaking-change footer token that isn’t exactly `BREAKING CHANGE:`** — wrong casing or spelling.
- **Using `!` but not providing migration details when the break isn’t obvious** — makes upgrades harder for consumers.
- **Not placing the description immediately after `: `** — extra whitespace or missing the space.
- **Writing a vague description** — like `update stuff` that doesn’t say what changed.
- **Using non-imperative phrasing** — like “Fixed bug” instead of “Fix bug”.
- **Ending the description with a period** — commit subjects should read like titles; trailing punctuation adds noise.
- **Writing an overly long description** — hard to scan; prefer <= 50 chars.
- **Adding a body without a blank line after the header** — causes parsing/formatting issues.
- **Not wrapping body lines at 72 characters** — reduces readability in terminals and typical `git log` views.
- **Writing a dense body without bullets when multiple points would be clearer** — reduces clarity.
- **Treating the body as a code dump / implementation notes instead of what/why** — loses context and makes future maintenance harder.
- **Including a body when the description is already self-explanatory** — unnecessary noise.
- **Adding footers without a blank line before them** — makes trailers harder to parse.
- **Using the wrong footer trailer format** — not `Token: value` or `Token #value`.
- **Using spaces in footer tokens** — use `Reviewed-by`, not `Reviewed by`.
- **Forgetting that multiple footers are allowed and jamming metadata into one line** — makes metadata harder to parse.
- **Assuming context that isn’t present in the message** — future readers won’t have it.
- **Not proofreading** — typos, inconsistent naming, unclear wording.
