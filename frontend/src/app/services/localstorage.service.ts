import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalstorageService {

  public setItemToStorage(key: string, value: any): void {
    localStorage.setItem(key, JSON.stringify(value));
  }

  public getItemFromStorage<T>(key: string): T | undefined {
      const dataFromStorage = localStorage.getItem(key)
      if (dataFromStorage) {
          return JSON.parse(dataFromStorage);
      } else {
          return undefined;
      }
  }

  public removeItemFromStorage(key: string): void {
      localStorage.removeItem(key);
  }

  public clear(): void {
      localStorage.clear();
  }
}
