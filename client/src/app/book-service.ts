import { HttpClient, HttpParams } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { BookSummary } from "./models";
import { Observable, map } from "rxjs";

const URL_API_RANDOM = 'http://localhost:8080/api/book/search'

@Injectable()
export class BookService {
    http = inject(HttpClient)

    getBooksByTitle(title: string): Observable<BookSummary[]> {
        const params = new HttpParams().set('query', title)

        return this.http.get<BookSummary[]>(URL_API_RANDOM, { params })
    }
}