import { Injectable, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DialogComponent } from './dialog/dialog.component';
import { MatDialog } from '@angular/material/dialog';


@Injectable({
  providedIn: 'root'
})

export class ToolService {
  @ViewChild(DialogComponent) private DialogComponent?: DialogComponent;

  constructor(
    private http: HttpClient,
    public dialog: MatDialog,

  ) { }



  doPost<T, R>(path: string, bodyJSON: T, successCallback: (result: R) => void, failedCallback: (error: Error) => void) {
    this.http.post<R>(
        '/' + path,
        JSON.stringify(bodyJSON),
      {
        headers: new HttpHeaders({
          'Content-Type': 'text/plain'
        })
      }).subscribe(
        {
          next: (successResult) => {
            if (successCallback) {
              successCallback(successResult);
            }
          },
          error: (failedResult) => {
            if (failedCallback) {
              failedCallback(failedResult);
            }
          }
        }
      );
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: {

      },
    });

    dialogRef.afterClosed().subscribe(() => {
      console.log('The dialog was closed');
    });
  }

}
