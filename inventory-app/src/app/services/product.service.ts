import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../common/product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private apiUrl : string = "http://localhost:80/api/v1/products";

  constructor(private httpClient : HttpClient) { 
    
  }

  getProductById(id: number) {
    return this.httpClient.get<Product>(`${this.apiUrl}/${id}`);
  }
  
  getProducts(orderBy?: string):Observable<Product[]> {
    let params = new HttpParams();
    if (orderBy) {
      params = params.set('orderBy', orderBy);
    }
    return this.httpClient.get<Product[]>(this.apiUrl + "/products", { params });
    
  }

  createProducts(formData: any):Observable<any>{
    return this.httpClient.post<Product>(this.apiUrl, formData);
  }

  deleteProduct(productId: number, userId: number) {
    return this.httpClient.delete(`${this.apiUrl}/${productId}?userId=${userId}`);
  }
}
