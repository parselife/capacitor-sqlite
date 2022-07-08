/*
 * @Author: alex
 * @Date: 2022-05-07 13:53:50
 * @LastEditTime: 2022-07-07 15:20:30
 * @LastEditors: maqingyi
 */
import { WebPlugin } from '@capacitor/core';

import type {
  CapacitorSqlitePlugin,
  SQLiteQueryOptions,
  SQLiteEnitytOptions,
  SQLiteEntityUpdateOptions,
} from './definitions';

export class SqlitePluginWeb
  extends WebPlugin
  implements CapacitorSqlitePlugin {
  async updateEntity(
    options: SQLiteEntityUpdateOptions,
  ): Promise<{ data: string | boolean }> {
    console.log(options);
    return { data: 'not supported' };
  }

  async delEntity(
    options: SQLiteEntityUpdateOptions,
  ): Promise<{ data: string | boolean }> {
    console.log(options);
    return { data: 'not supported' };
  }

  async insertEntity(
    options: SQLiteEnitytOptions,
  ): Promise<{ data: string | boolean }> {
    console.log(options);
    return { data: 'not supported' };
  }

  async queryForList(
    options: SQLiteQueryOptions,
  ): Promise<{ data: any[] }> {
    console.log(options);
    return { data: [{ data: 'not supported' }] };
  }

  async queryForObject(
    options: SQLiteQueryOptions,
  ): Promise<{ data: any }> {
    console.log(options);
    return { data: { data: 'not supported' } };
  }

  async loadDatabase(options: {
    dbPath: string;
    dbName: string;
  }): Promise<{ data: string | boolean }> {
    console.log(options);
    return { data: 'not supported' };
  }

  /**
   * 测试方法
   * @param options 参数
   * @returns 相同值
   */
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
