.data
	key: .space 9
	msg: .space 17
	ret: .space 16
	
	intro: .asciiz "0-encryption, 1-decryption\n"

.text
main:
	li $v0 4			#display intro
	la $a0 intro
	syscall
	# load operation
	li $v0 5
	syscall
	move $t0 $v0
	bgt $t0 1 main
	blt $t0 0 main
	# read key
	li $v0 8
	la $a0 key
	li $a1 9
	syscall
	# print new line
	li $v0 11
	li $a0 '\n'
	syscall
	# read msg
	li $v0 8
	la $a0 msg
	li $a1 17
	syscall
	# load strings to registers
	la $t7 key
	la $t8 msg
	la $t9 ret
	
	beqz $t0 encrypt
	
	decrypt:
		lb $t1 0($t7)
		lb $t2 0($t8)
		beq $t2 '\n' end		# end program if end of message
		beq $t2 '\0' end			
		beq $t1 '\n' reset1		# if not end of key - skip resetting key
		beq $t1 '\0' reset1
		j next1
			reset1:
			la $t7 key
			lb $t1 0($t7)
		next1:
		addi $t2 $t2 65
		sub $t2 $t2 $t1
		bge $t2 'A' next2
			addi $t2 $t2 26
		next2:
		sb $t2 ($t9)
		
		addi $t7 $t7 1			# increment the address
		addi $t8 $t8 1
		addi $t9 $t9 1
		j decrypt
	
	encrypt:
		lb $t1 0($t7)
		lb $t2 0($t8)
		beq $t2 '\n' end	
		beq $t2 '\0' end		
		beq $t1 '\n' reset2		# if not end of key - skip resetting key		
		beq $t1 '\0' reset2
		j next3
			reset2:
			la $t7 key
			lb $t1 0($t7)
		next3:
		subi $t2 $t2 65
		add $t2 $t1 $t2
		ble $t2 'Z' next4
			subi $t2 $t2 26		# loop back when greater than 'Z'
		next4:
		sb $t2 ($t9)
		
		addi $t7 $t7 1			# increment the address
		addi $t8 $t8 1
		addi $t9 $t9 1
		j encrypt
	end:
	la $a0 ret
	li $v0 4
	syscall
	
	li $v0 10
	syscall					#terminate
	
