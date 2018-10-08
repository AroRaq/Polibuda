.text

.data
main:	li $t0 0							#state of board
	li $t1 0							#user's moves
	li $t2 0 						#bot's moves






finished:
	xori $t9 $t0 0x001f
	beqz $t9 true
	
	
	
	
	true:
	li $a0 1
	jr $ra

findwin:		#returns square of winning move
	
	
countbits:	#counts bits in int in $a0 (uses $a0, $a1, $a2, $a3)
	li $a1 1
	li $a3 0
	andi $a2 $a1 $a0
	beqz $a2 loop_c
	addi $a3 $a3 1
	
	loop_c:	sll $a1 $a1 1
		beq $a1 256 end_c
		and $a2 $a0 $a1
		beqz $a2 loop_c
		addi $a3 $a3 1
		j loop_c
	
	end_c:	move $a3 $a0
		jr $ra
		
