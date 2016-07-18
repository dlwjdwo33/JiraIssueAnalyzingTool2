# -*- coding:utf-8 -*-
import random
import threading
import smtplib
import logging
import sys
import email
from emaildata.text import Text
from email.mime.text import MIMEText
import datetime
import re

from email.header import Header

# 로그관련 설정
log = logging.getLogger(__name__)
out_hdlr = logging.StreamHandler(sys.stdout)
out_hdlr.setFormatter(logging.Formatter('%(asctime)s %(message)s'))
out_hdlr.setLevel(logging.INFO)
log.addHandler(out_hdlr)
log.setLevel(logging.INFO)


# 발신자 아이디 Random으로 생성하기 위한 method
def randstring(length=10):
    valid_letters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    return ''.join((random.choice(valid_letters) for i in xrange(length)))


# 발신자
sender = randstring()

# 수신자 pool
receiver = ['test0001@alpha.terracetech.co.kr', 'test0002@alpha.terracetech.co.kr', 'test0003@alpha.terracetech.co.kr',
            'test0004@alpha.terracetech.co.kr', 'test0005@alpha.terracetech.co.kr', 'test0006@alpha.terracetech.co.kr',
            'test0007@alpha.terracetech.co.kr', 'test0008@alpha.terracetech.co.kr', 'test0009@alpha.terracetech.co.kr']

Contents = {}
HEADER = {}

Contents['HEADER'] = HEADER


def readEML():
    with open("C:\\textfile2.eml", "r") as emlFile:
        msg = email.message_from_file(emlFile)

    for part in msg.walk():
        # Get Header
        if (part.get_content_type() == "multipart/mixed"):
            HEADER['keys'] = part.keys()
            HEADER['values'] = part.values()


# eml 파일 open
fp = open("C:\\textfile2.eml", 'r')
message = MIMEText(fp.read())
fp.close()

message["Subject"] = Header(s='제목', charset="utf-8").encode()
message["From"] = str(Header(randstring(), 'utf-8'))
message["To"] = 'test0012@alpha.terracetech.co.kr'

# 메일 내용을 빼오기 위해 emaildata library 사용
content = email.message_from_file(open("C:\\textfile2.eml"))
text = Text.text(content)
html = Text.html(content)

# mailsever , 스레드갯수 , 실행시간을 입력
mailserver = raw_input("메일서버를 입력하세요 :")
threads = input("스레드 갯수를 입력하세요 :")
opertime = input("실행시간을 입력하세요 : ")

# while문을 몇초간 돌릴지 설정하기 위해 끝나는 시간 설정
startnowtime = datetime.datetime.now()
finishtime = startnowtime + datetime.timedelta(seconds=opertime)


class SendMail(threading.Thread):
    def __init__(self, fro, to, subject, contents):
        threading.Thread.__init__(self)
        self.fro = fro
        self.to = to
        self.subject = subject
        self.contents = contents

    def run(self):
        server = smtplib.SMTP(mailserver, 25)
        global succount
        succount = 0
        global falcount
        falcount = 0
        while True:
            nowtime = datetime.datetime.now()
            try:
                server.sendmail(self.fro, self.to, self.contents)
                succount += 1
                log.info(threading.currentThread().getName() + " :" + "Mail transfer success")
            except smtplib.SMTPException:
                falcount += 1
                log.info(threading.currentThread().getName() + " :" + "Mail transfer fail")

            if (finishtime.strftime('%Y-%m-%d %H:%M:%S') <= nowtime.strftime('%Y-%m-%d %H:%M:%S')):
                server.quit()
                thread.join()
                print threading.currentThread().getName() + " " + "success : ",
                print succount
                print threading.currentThread().getName() + " " + "fail : ",
                print falcount
                break




for thread in range(threads):
    thread = SendMail(message["From"], message["To"], message['Subject'], html)
    thread.start()




