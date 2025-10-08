0. 透過系統管理員權限打開命令提示字元

1. 安裝 chocolatey cli
   @"%SystemRoot%\System32\WindowsPowerShell\v1.0\powershell.exe" -NoProfile -InputFormat None -ExecutionPolicy Bypass -Command "[System.Net.ServicePointManager]::SecurityProtocol = 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))" && SET "PATH=%PATH%;%ALLUSERSPROFILE%\chocolatey\bin"

2. 安裝 openjdk 20
   choco install jdk20

3. 安裝 spring boot cli
   choco install spring-boot-cli

4. 安裝 nvm
   choco install nvm

5. 安裝 nodejs
   nvm install lts

6. 安裝angular-cli
   npm install -g @angular/cli --force

7. 產生spring專案
   spring init --dependencies=web demo

8. 產生angular專案
   8.1 專案資料夾底下
   cd demo
   8.2 產生angular專案
   ng new angularDemo
   8.3 回答問題
   ? Which stylesheet format would you like to use? CSS
   ? Do you want to enable Server-Side Rendering (SSR) and Static Site Generation (SSG/Prerendering)? Yes
9. 用vscode打開專案資料夾
   code ..\demo

10. 開啟DemoApplication.java

11. Ctrl + Shift + D打開debug頁面

12. 按下Run and debug，啟動spring service

13. 進入專案資料夾中的angular資料夾angularDemo

14. 啟動angular service
    ng serve
15. 打開前端URL http://localhost:4200/

16. 打開後端URL http://localhost:8080/
