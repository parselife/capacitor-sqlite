# capacitor-sqlite-android

sqlite

## Install

```bash
npm install capacitor-sqlite-android
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`loadDatabase(...)`](#loaddatabase)
* [`queryForObject(...)`](#queryforobject)
* [`queryForList(...)`](#queryforlist)
* [`insertEntity(...)`](#insertentity)
* [`updateEntity(...)`](#updateentity)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

测试方法

| Param         | Type                            | Description |
| ------------- | ------------------------------- | ----------- |
| **`options`** | <code>{ value: string; }</code> | echo params |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### loadDatabase(...)

```typescript
loadDatabase(options: { dbPath?: string; dbName: string; }) => Promise<{ value: string | boolean; }>
```

加载数据库

| Param         | Type                                              | Description                   |
| ------------- | ------------------------------------------------- | ----------------------------- |
| **`options`** | <code>{ dbPath?: string; dbName: string; }</code> | dbPath: 数据库文件位置 dbName: 数据文件名 |

**Returns:** <code>Promise&lt;{ value: string | boolean; }&gt;</code>

--------------------


### queryForObject(...)

```typescript
queryForObject(options: SQLiteQueryOptions) => Promise<{ value: Record<string, unknown>; }>
```

查询单个对象

| Param         | Type                                                              | Description                                                       |
| ------------- | ----------------------------------------------------------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#sqlitequeryoptions">SQLiteQueryOptions</a></code> | 查询参数 {@link <a href="#sqlitequeryoptions">SQLiteQueryOptions</a>} |

**Returns:** <code>Promise&lt;{ value: <a href="#record">Record</a>&lt;string, unknown&gt;; }&gt;</code>

--------------------


### queryForList(...)

```typescript
queryForList(options: SQLiteQueryOptions) => Promise<{ value: Record<string, unknown>[]; }>
```

查询列表

| Param         | Type                                                              | Description                                                       |
| ------------- | ----------------------------------------------------------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#sqlitequeryoptions">SQLiteQueryOptions</a></code> | 查询参数 {@link <a href="#sqlitequeryoptions">SQLiteQueryOptions</a>} |

**Returns:** <code>Promise&lt;{ value: <a href="#record">Record</a>&lt;string, unknown&gt;[]; }&gt;</code>

--------------------


### insertEntity(...)

```typescript
insertEntity(options: SQLiteEnitytOptions) => Promise<{ value: string | boolean; }>
```

保存实体

| Param         | Type                                                                | Description                                                         |
| ------------- | ------------------------------------------------------------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#sqliteenitytoptions">SQLiteEnitytOptions</a></code> | 实体参数 {@link <a href="#sqliteenitytoptions">SQLiteEnitytOptions</a>} |

**Returns:** <code>Promise&lt;{ value: string | boolean; }&gt;</code>

--------------------


### updateEntity(...)

```typescript
updateEntity(options: SQLiteEntityUpdateOptions) => Promise<{ value: string | boolean; }>
```

更新实体

| Param         | Type                                                                            | Description                                                                       |
| ------------- | ------------------------------------------------------------------------------- | --------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#sqliteentityupdateoptions">SQLiteEntityUpdateOptions</a></code> | 更新实体参数 {@link <a href="#sqliteentityupdateoptions">SQLiteEntityUpdateOptions</a>} |

**Returns:** <code>Promise&lt;{ value: string | boolean; }&gt;</code>

--------------------


### Interfaces


#### SQLiteQueryOptions

数据库查询对象

| Prop                | Type                  | Description                     |
| ------------------- | --------------------- | ------------------------------- |
| **`tblName`**       | <code>string</code>   | 操作表名                            |
| **`selection`**     | <code>string</code>   | 查询条件 eg: a=? and b like '%?'    |
| **`selectionArgs`** | <code>string[]</code> | 查询的参数 替换 selection 中的 ?         |
| **`returnColumns`** | <code>string[]</code> | 返回的列名 为空则返回所有                   |
| **`groupBy`**       | <code>string</code>   | groupby 语句 eg: name             |
| **`having`**        | <code>string</code>   | having 语句 eg: sum(area) &gt; 20 |
| **`orderBy`**       | <code>string</code>   | order by 语句 eg: time desc       |
| **`limit`**         | <code>number</code>   | limit 数量 eg: 10                 |


#### SQLiteEnitytOptions

数据库实体保存对象

| Prop          | Type                              | Description                                                         |
| ------------- | --------------------------------- | ------------------------------------------------------------------- |
| **`tblName`** | <code>string</code>               | 操作的表名                                                               |
| **`columns`** | <code>SQLiteEntityColumn[]</code> | 保存的数据列 {@link <a href="#sqliteentitycolumn">SQLiteEntityColumn</a>} |


#### SQLiteEntityColumn

数据库实体数据列

| Prop           | Type                                     | Description        |
| -------------- | ---------------------------------------- | ------------------ |
| **`name`**     | <code>string</code>                      | 字段名称               |
| **`value`**    | <code>string \| number \| boolean</code> | 字段值                |
| **`nullable`** | <code>boolean</code>                     | 是否允许为null 默认 false |


#### SQLiteEntityUpdateOptions

实体更新对象

| Prop                  | Type                              | Description                                                         |
| --------------------- | --------------------------------- | ------------------------------------------------------------------- |
| **`tblName`**         | <code>string</code>               | 操作的表名                                                               |
| **`columns`**         | <code>SQLiteEntityColumn[]</code> | 保存的数据列 {@link <a href="#sqliteentitycolumn">SQLiteEntityColumn</a>} |
| **`whereClause`**     | <code>string</code>               | where 条件 eg: id=?                                                   |
| **`whereClauseArgs`** | <code>string[]</code>             | where 条件参数 ? 的值                                                     |


### Type Aliases


#### Record

Construct a type with a set of properties K of type T

<code>{ [P in K]: T; }</code>

</docgen-api>
