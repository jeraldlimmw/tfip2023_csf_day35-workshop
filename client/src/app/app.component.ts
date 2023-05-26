import { AfterViewInit, Component, OnInit, ViewChild, inject } from '@angular/core';
import { Observable, Subject, debounceTime, filter, mergeMap, tap } from 'rxjs';
import { BookSummary } from './models';
import { BookService } from './book-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{

  bookSvc = inject(BookService)
  
  input = new Subject<string>

  books$!: Observable<BookSummary[]>

  // helps to control the flow of information even though we already have the observable here
  ngOnInit(): void {
      this.books$ = this.input.pipe(
        filter(title => title.trim().length > 0),
        debounceTime(300),
        // map(title => this.bookSvc.getBooksByTitle(title)) returns an observable
        // and not the contents of the observable i.e. obs of obs of bookSummary[]
        mergeMap(title => this.bookSvc.getBooksByTitle(title))
        // mergeMap will "collapse" the values the observable holds into one observable
      )
  }

  keypressed(text: any) {
    console.info('>>input: ', text)
    this.input.next(text as string)
  }
}
