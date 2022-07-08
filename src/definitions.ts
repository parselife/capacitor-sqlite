/*
 * @Author: alex
 * @Date: 2022-05-07 13:53:50
 * @LastEditTime: 2022-07-07 15:19:20
 * @LastEditors: maqingyi
 */

/**
 * 数据库实体保存对象
 */
export interface SQLiteEnitytOptions {
  /**
   * 操作的表名
   */
  tblName: string;

  /**
   * 保存的数据列 {@link SQLiteEntityColumn}
   */
  columns: SQLiteEntityColumn[];
}

/**
 * 实体更新对象
 */
export interface SQLiteEntityUpdateOptions {
  /**
   * 操作的表名
   */
  tblName: string;

  /**
   * 保存的数据列 {@link SQLiteEntityColumn}
   */
  columns: SQLiteEntityColumn[];

  /**
   * where 条件 eg: id=?
   */
  whereClause: string;

  /**
   * where 条件参数 ? 的值
   */
  whereClauseArgs: string[];
}

/**
 * 数据库实体数据列
 */
export interface SQLiteEntityColumn {
  /**
   * 字段名称
   */
  name: string;

  /**
   * 字段值
   */
  value: string | number | boolean;

  /**
   * 是否允许为null 默认 false
   */
  nullable?: boolean;
}

/**
 * 数据库查询对象
 */
export interface SQLiteQueryOptions {
  /**
   * 操作表名
   */
  tblName: string;

  /**
   * 查询条件
   * eg: a=? and b like '%?'
   */
  selection?: string;

  /**
   * 查询的参数 替换 selection 中的 ?
   */
  selectionArgs?: string[];

  /**
   * 返回的列名 为空则返回所有
   */
  returnColumns?: string[];

  /**
   * groupby 语句
   * eg:  name
   */
  groupBy?: string;

  /**
   * having 语句
   * eg: sum(area) > 20
   */
  having?: string;

  /**
   * order by 语句
   * eg: time desc
   */
  orderBy?: string;

  /**
   *  limit 数量
   * eg: 10
   */
  limit?: number;
}
export interface CapacitorSqlitePlugin {
  
  /**
   * 测试方法
   * @param options echo params
   */
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
   * 加载数据库
   * @param options dbPath: 数据库文件位置 dbName: 数据文件名
   */
  loadDatabase(options: {
    dbPath?: string;
    dbName: string;
  }): Promise<{ data: string | boolean }>;

  /**
   * 查询单个对象
   * @param options 查询参数 {@link SQLiteQueryOptions}
   */
  queryForObject(
    options: SQLiteQueryOptions,
  ): Promise<{ data: any }>;

  /**
   * 查询列表
   * @param options 查询参数 {@link SQLiteQueryOptions}
   */
  queryForList(
    options: SQLiteQueryOptions,
  ): Promise<{ data: any[] }>;

  /**
   * 保存实体
   * @param options 实体参数 {@link SQLiteEnitytOptions}
   */
  insertEntity(
    options: SQLiteEnitytOptions,
  ): Promise<{ data: string | boolean }>;

  /**
   * 更新实体
   * @param options 更新实体参数 {@link SQLiteEntityUpdateOptions}
   */
  updateEntity(
    options: SQLiteEntityUpdateOptions,
  ): Promise<{ data: string | boolean }>;

  /**
   * 删除实体
   * @param options 更新实体参数 {@link SQLiteEntityUpdateOptions}
   */
   delEntity(
    options: SQLiteEntityUpdateOptions,
  ): Promise<{ data: string | boolean }>;
}
