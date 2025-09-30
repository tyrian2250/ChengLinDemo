import { Injectable, ViewChild } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { DialogComponent } from './dialog/dialog.component';
import { MatDialog, MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


@Injectable({
  providedIn: 'root'
})

export class ToolService {
  @ViewChild(DialogComponent) private DialogComponent?: DialogComponent;

  constructor(
    private http: HttpClient,
    public dialog: MatDialog,

  ) { }



  doPost(path: string, bodyJSON: any, successCallback: Function, failedCallback: Function) {
    this.http.post<any>(
      '/' + path,
      JSON.stringify(bodyJSON),
      {
        headers: new HttpHeaders({
          'Content-Type': 'text/plain'
        })
      }).subscribe(
        {
          next: (successResult) => {
            successCallback && successCallback(successResult);
          },
          error: (failedResult) => {
            failedCallback && failedCallback(failedResult);
          }
        }
      );
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DialogComponent, {
      data: {

      },
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

}
