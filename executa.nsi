; Java Launcher Atualiza Tabwin
;------------------------------------
Name "AtualizaTabwin"
Caption "Atualiza Tabwin"
Icon "icone48.ico"
OutFile "atualizatabwin.exe"

VIAddVersionKey "ProductName" "Atualiza Tabwin"
VIAddVersionKey "Comments" "Atualiza Tabwin"
VIAddVersionKey "CompanyName" "JCWJ"
VIAddVersionKey "LegalTrademarks" "AtualizaTabwin e uma marca registrada de JCWJ"
VIAddVersionKey "LegalCopyright" "JCWJ"
VIAddVersionKey "FileDescription" "Atualiza Tabwin"
VIAddVersionKey "FileVersion" "1.0.0"
VIProductVersion "1.0.0.1"
 
SilentInstall silent
AutoCloseWindow true
ShowInstDetails nevershow

;You want to change the next two lines too
!define CLASSPATH ".;lib;AtUpdate.jar"
!define CLASS "atupdate.AtUpdateMain"
 
Section ""
  Call GetJRE
  Pop $R0
 
  ; change for your purpose (-jar etc.)
  StrCpy $0 '"$R0" -classpath "${CLASSPATH}" ${CLASS}'
  
  SetOutPath $EXEDIR
  ExecWait $0
SectionEnd
 
Function GetJRE
;
;  Find JRE (javaw.exe)
;  1 - in .\jre directory (JRE Installed with application)
;  2 - in JAVA_HOME environment variable
;  3 - in the registry
;  4 - assume javaw.exe in current dir or PATH
 
  Push $R0
  Push $R1
 
  ; 1) Check local JRE
  CheckLocal:
    ClearErrors
    StrCpy $R0 "$EXEDIR\jre\bin\javaw.exe"
    IfFileExists $R0 JreFound
    StrCpy $R0 ""
	
  ; 2) Check for JAVA_HOME
  CheckJavaHome:
    ClearErrors
    ReadEnvStr $R0 "JAVA_HOME"
    StrCpy $R0 "$R0\bin\javaw.exe"
    IfErrors 0 JreFound
 
  ; 3) Check for registry
  CheckRegistry:
    ClearErrors
    ReadRegStr $R1 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
    ReadRegStr $R0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\$R1" "JavaHome"
    StrCpy $R0 "$R0\bin\javaw.exe"
 
  IfErrors 0 JreFound
  StrCpy $R0 "javaw.exe"
 
  JreFound:
    Pop $R1
    Exch $R0
FunctionEnd