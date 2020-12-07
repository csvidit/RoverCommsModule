	br main
hpPtr:	.addrss heap
MASK_lsb:	.equate 1
MASK_2ls:	.equate 0x0003
MASK_4ls:	.equate 0x000f
MASK_7ls:	.equate 0x007f
MASK_9ls:	.equate 0x01ff
MASK_10l:	.equate 0x03ff

; Command Message Expanded Struct
CM_size:	.equate 26

; Time is big-endian
CM_time:	.equate 0
CM_time2:	.equate 2
CM_time3:	.equate 4

; Wait condition is big-endian
CM_wait:	.equate 6
CM_wait2:	.equate 8

CM_light:	.equate 10
CM_long:	.equate 12
CM_lat:	.equate 14
CM_charg:	.equate 16
CM_atemp:	.equate 18
CM_itemp:	.equate 20
CM_wind:	.equate 22
CM_mode:	.equate 24

; CM_print(*msg)
cp_msg:	.equate 2

CM_print:	ldwx 0,i
	
cp_for:	cpwx CM_size,i
	brge cp_end
	
	deco cp_msg,sfx
	stro newline,d
	br cp_for
	
cp_end:	ret

; asbyte(byte) -> byte
ab_char:	.equate 4
ab_ret:	.equate 2
asbyte:	stro call_ab,d
	hexo ab_char,s
	stro parmsep,d
	
	ldwa ab_char,s
	
	; Convert char to uppercase
	cpwa 'Z',i	; Lowercase is higher than uppercase
	brle ab_dcod
	suba 0x20,i
	
ab_dcod:	cpwa '0',i
	brlt ab_err
	cpwa '9',i
	brle ab_dig
	
	cpwa 'A',i
	brlt ab_err
	cpwa 'F',i
	brle ab_let
	br ab_err
	
ab_dig:	suba 0x30,i
	br ab_end
	
ab_let:	suba 0x37,i
	br ab_end
	
ab_err:	stro ab_ermsg,d
	hexo ab_char,s
	stop
	
ab_end:	stwa ab_ret,s
	hexo ab_ret,s
	stro newline,d
	ret

; hexin(*buf, len)
hi_buf:	.equate 4
hi_len:	.equate 6

hi_byte:	.equate 0

hexin:	subsp 2,i
	ldwx 0,i
	
hi_for:	cpwx hi_len,s
	brgt hi_end
	
	ldwa 0,i
	ldba charIn,d	; Filter spaces/newlines
	cpwa ' ',i
	breq hi_for
	cpwa '\n',i
	breq hi_for
	
	subsp 4,i
	stwa 2,s
	call asbyte
	ldwa 0,i
	addsp 4,i
	
	movaflg	; clear carry bit (copies most significant bits, which are empty)
	ldwa -4,s	; Grab the return value of as_byte
	rola
	rola
	rola
	rola
	stwa hi_byte,s
	
hi_two:	ldwa 0,i
	ldba charIn,d	; Filter spaces/newlines
	cpwa ' ',i
	breq hi_two
	cpwa '\n',i
	breq hi_two
	
	subsp 4,i
	stwa 2,s
	call asbyte
	ldwa 0,i
	ldwa 0,s
	addsp 4,i
	
	ora hi_byte,s
	stba hi_buf,sfx
	
	addx 1,i
	br hi_for
	
hi_end:	addsp 2,i
	ret

; Command Message Compressed Struct
MSG_size:	.equate 21

; Time is big-endian
MSG_time:	.equate 0
MSG_tim2:	.equate 2
MSG_tim3:	.equate 4

; Wait condition is big-endian
MSG_wait:	.equate 6
MSG_wat2:	.equate 8

MSG_lite:	.equate 10
MSG_long:	.equate 12

MSG_lat:	.equate 14
MSG_chrg:	.equate 15

MSG_atmp:	.equate 17
MSG_wind:	.equate 19
MSG_itmp:	.equate 20

; parse_MSG(*buf, *msg)
pm_buf:	.equate 4	; Compressed
pm_msg:	.equate 6	; Expanded

pm_wrkv:	.equate 0

pars_MSG:	subsp 2,i
	
	ldwx 0,i
pm_for:	cpwx 12,i
	brge pm_lat
	
	ldwa pm_buf,sfx
	stwa pm_msg,sfx
	
	addx 2,i
	br pm_for
	
	; Latitude and Charge Level
pm_lat:	ldwx MSG_lat,i
	ldwa pm_buf,sfx
	rora
	
	ldwx CM_lat,i
	stwa pm_msg,sfx
	
	ldwa 0,i	; Clear C for rotate
	movaflg
	ldwx MSG_lat,i
	ldwa pm_buf,sfx
	
	call rola8
	anda MASK_9ls,i
	
	ldwx MSG_chrg,i
	ldba pm_buf,sfx
	
	ldwx CM_charg,i
	stwa pm_msg,sfx
	
	; Ambient Temp
	ldwx MSG_atmp,i
	ldwa pm_buf,sfx
	
	call rora6
	anda MASK_10l,i
	
	ldwx CM_atemp,i
	stwa pm_msg,sfx
	
	; Mission Mode
	ldwx MSG_atmp,i
	ldwa pm_buf,sfx
	
	rora
	rora
	anda MASK_4ls,i
	
	ldwx CM_mode,i
	stwa pm_msg,sfx
	
	; Wind Speed
	ldwx MSG_atmp,i
	ldwa pm_buf,sfx
	
	anda MASK_2ls,i
	stba pm_wrkv,s
	ldwa pm_wrkv,s
	rora
	stwa pm_wrkv,s
	
	ldwx MSG_wind,i
	ldba pm_buf,sfx
	
	rora
	anda MASK_7ls,i
	ora pm_wrkv,s
	
	ldwx CM_wind,i
	stwa pm_msg,sfx
	
	; Internal Temp
	ldwx MSG_wind,i
	ldwa pm_buf,sfx
	
	anda MASK_9ls,i
	
	ldwx CM_itemp,i
	stwa pm_msg,sfx
	
	ret

; Rotate A left 8 times (DOES NOT CLEAR C)
; rola8()
rola8:	rola
	rola
	rola
	rola
	rola
	rola
	rola
	rola
	rola
	ret

; Rotate A right 6 times (DOES NOT CLEAR C)
; rora6()
rora6:	rora
	rora
	rora
	rora
	rora
	rora
	ret

; malloc()
; Precondition: A contains a number of bytes
; Postcondition: X contains a pointer to bytes
malloc:	ldwx hpPtr,d
	adda hpPtr,d
	stwa hpPtr,d
	ret

; main()
buf:	.equate 0
msg:	.equate 2
	
main:	subsp 2,i
	
	ldwa CM_size,i
	call malloc
	stwx msg,s
	
	ldwa MSG_size,i
	call malloc
	stwx buf,s
	
	; Call hexin
	subsp 4,i
	stwx 0,s
	
	ldwa MSG_size,i
	stwa 2,s
	
	call hexin
	addsp 4,i
	
	; Call parse_MSG
	ldwa buf,s
	stwa -4,s
	
	ldwa msg,s
	stwa -2,s
	
	subsp 4,i
	call pars_MSG
	addsp 4,i
	
	; Call CM_print
	ldwa msg,s
	
	subsp 2,i
	stwa 0,s
	call CM_print
	addsp 2,i
	
	addsp 2,i
	stop
	
ab_ermsg:	.ascii "Invalid hex digit: \x00"
call_ab:	.ascii "Call as_byte: \x00"
parmsep:	.ascii ", "
newline:	.ascii "\n\x00"
heap:	.block 1
.end

