# 一键生成树状文本
tree /F

# 本地测试通过后发版
git tag -a v2.3.0 -m "release 2.3.0"
git push origin v2.3.0

# 导出项目源码到一个文件
PS D:\Androidstudio\Android Project\androidtoolbox\app\src> Get-ChildItem -Path .\main\ -Filter *.java -Recurse | Get-Content > allcode.txt

# git commit规范化格式
https://ai.dangbei.com/share/CWHum9tiKQ